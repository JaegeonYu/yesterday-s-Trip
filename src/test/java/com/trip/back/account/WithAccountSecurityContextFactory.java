package com.trip.back.account;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import com.trip.back.security.JwtAuthentication;
import com.trip.back.security.JwtAuthenticationToken;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WithAccountSecurityContextFactory implements WithSecurityContextFactory<WithAccount>{
	private final AccountService accountService;

	@Override
	public SecurityContext createSecurityContext(WithAccount withAccount) {
		String nickname = withAccount.value();
		Account account = Account.builder().nickname(nickname).email(nickname+"@email.com").password("1q2w3e4r").build();
		
		
		accountService.join(account);
		Account saveAccount = accountService.findByEmail(nickname+"email.com");
		String[] roles = new String[] {Role.USER.name()};
		List<GrantedAuthority> authorities =  Arrays.stream(roles).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
		
		
		JwtAuthenticationToken authentication =
	              new JwtAuthenticationToken(new JwtAuthentication(saveAccount.getId(), saveAccount.getNickname(), saveAccount.getEmail()), null, authorities);
	            
		SecurityContext context = SecurityContextHolder.createEmptyContext();

        context.setAuthentication(authentication);
	      
		
		return context;
	}
	
	

}
