package com.trip.back.security;


import static org.springframework.security.core.authority.AuthorityUtils.createAuthorityList;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import com.trip.back.account.Account;
import com.trip.back.account.AccountService;
import com.trip.back.account.Role;
import com.trip.back.auth.dto.AuthenticationRequest;
import com.trip.back.auth.dto.AuthenticationResult;


public class JwtAuthenticationProvider implements AuthenticationProvider{
	 private final Jwt jwt;

	  private final AccountService accountService;

	  public JwtAuthenticationProvider(Jwt jwt, AccountService accountService) {
	    this.jwt = jwt;
	    this.accountService = accountService;
	  }

	  @Override
	  public boolean supports(Class<?> authentication) {
	    return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
	  }


	  @Override
	  public Authentication authenticate(Authentication authentication) throws AuthenticationException{
		  
		  JwtAuthenticationToken authenticationToken = (JwtAuthenticationToken) authentication;
	    return processUserAuthentication(authenticationToken.authenticationRequest());
	  }

	  private Authentication processUserAuthentication(AuthenticationRequest request) {
	    try {
	      Account account = accountService.login(request.getPrincipal(), request.getCredentials());
	      account.afterLogin();
	      JwtAuthenticationToken authenticated =

	        new JwtAuthenticationToken(new JwtAuthentication(account.getId(), account.getNickname(), account.getEmail()), null, createAuthorityList(Role.USER.value()));
	      String apiToken = account.newApiToken(jwt, new String[] {Role.USER.value()});
	      String refreshToken = account.newRefreshApiToken(jwt,new String[] {Role.USER.value()});
	      authenticated.setDetails(new AuthenticationResult(apiToken, refreshToken, account));
//	      authenticated.setDetails(account);
	      return authenticated;
	    } catch(Exception e) {
	    	e.printStackTrace();
	    	throw new RuntimeException("로그인 실패");
	    }
//	    catch (NotFoundException e) {
//	      throw new UsernameNotFoundException(e.getMessage());
//	    } catch (IllegalArgumentException e) {
//	      throw new BadCredentialsException(e.getMessage());
//	    } catch (DataAccessException e) {
//	      throw new AuthenticationServiceException(e.getMessage(), e);
//	    }
	  }
}
