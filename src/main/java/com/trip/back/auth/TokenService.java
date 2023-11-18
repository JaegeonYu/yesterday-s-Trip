package com.trip.back.auth;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Pattern;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trip.back.account.Account;
import com.trip.back.account.AccountMapper;
import com.trip.back.account.Role;
import com.trip.back.security.Jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class TokenService {
	private static final Pattern BEARER = Pattern.compile("^Bearer$", Pattern.CASE_INSENSITIVE);
	private final TokenMapper tokenRepository;
	private final AccountMapper accountRepository;
	private final Jwt jwt;
	
	public String refreshAccess(String token){
		String validToken = obtainToken(token);
		if(validToken != null) {
			Jwt.Claims claims = jwt.verify(validToken);
			TokenDto to = tokenRepository.findByAccountId(claims.userKey);
			 if(validToken.equals(to.getRefreshToken())){
				 Account account = accountRepository.findByEmail(claims.email);
				 String apiToken = account.newApiToken(jwt,  new String[] {Role.USER.value()});
				 return apiToken;
			 }
		}
		throw new RuntimeException();
	}
	
	private String obtainToken(String token) {
		if(token != null) {
			try {
				token = URLDecoder.decode(token, "UTF-8");
				String[] tokens = token.split(" ");
				if(tokens.length == 2) {
					String scheme = tokens[0];
					String credentails = tokens[1];
					return BEARER.matcher(scheme).matches() ? credentails : null;
				}
			}catch(Exception e) {
				log.error("Jwt ObtainAuthorization Process error");
			}
		}
		return null;
	}
	
	public void delete(Long accountId) {
		tokenRepository.delete(accountId);
	}
	
	public void ifExistRefresh(String email) {
		Account account = accountRepository.findByEmail(email);
		TokenDto token = tokenRepository.findByAccountId(account.getId());
		if(token != null) {
			delete(account.getId());
		}
	}
	
}
