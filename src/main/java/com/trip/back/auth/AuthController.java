package com.trip.back.auth;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.interfaces.Claim;
import com.trip.back.account.Account;
import com.trip.back.account.AccountMapper;
import com.trip.back.account.Role;
import com.trip.back.auth.dto.AuthenticationRequest;
import com.trip.back.auth.dto.AuthenticationResult;
import com.trip.back.auth.dto.AuthenticationResultDto;
import com.trip.back.auth.dto.RefreshRequest;
import com.trip.back.security.Jwt;
import com.trip.back.security.Jwt.Claims;
import com.trip.back.security.JwtAuthentication;
import com.trip.back.security.JwtAuthenticationToken;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
	
	 private final AuthenticationManager authenticationManager;
	 private final TokenMapper tokenRepository;
	 private final AccountMapper accountRepository;
	 private final Jwt jwt;
	 
	 @PostMapping
	  public ResponseEntity<AuthenticationResultDto> authentication(@RequestBody @Valid AuthenticationRequest authRequest) {
		 log.info("authRequest : {}", authRequest.toString());
	    try {
	      JwtAuthenticationToken authToken = new JwtAuthenticationToken(authRequest.getPrincipal(), authRequest.getCredentials());
	      
	      Authentication authentication = authenticationManager.authenticate(authToken);
	      SecurityContextHolder.getContext().setAuthentication(authentication);
	      log.info("after Login detials : {}", authentication.getDetails().toString());
	      return ResponseEntity.ok(
	        new AuthenticationResultDto((AuthenticationResult)authentication.getDetails())
	      );
	    } catch (AuthenticationException e) {
	    	throw new RuntimeException("컨트롤러 로그인 실패");
//	      throw new UnauthorizedException(e.getMessage());
	    }
	  }
	 
	 @PostMapping("/refresh")
	 public ResponseEntity<AccessRefreshDto> refresh(HttpServletRequest request){
		 HttpStatus status = HttpStatus.ACCEPTED;
		 String token = request.getHeader("refreshToken");
		 Jwt.Claims claims = jwt.verify(token);
		 if(token.equals(tokenRepository.findByAccountId(claims.userKey).getRefresh())){
			 Account account = accountRepository.findByEmail(claims.email);
			 String apiToken = account.newApiToken(jwt,  new String[] {Role.USER.value()});
			 return ResponseEntity.ok(new AccessRefreshDto(apiToken));
		 }
		
		throw new RuntimeException();
	 }
	 
	 
	 @GetMapping("/hello")
	 public ResponseEntity<?> hello(@AuthenticationPrincipal JwtAuthentication authenticaiton){
		 
		 return ResponseEntity.ok("auth check");
	 }

}
