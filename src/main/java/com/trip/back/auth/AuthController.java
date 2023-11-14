package com.trip.back.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trip.back.auth.dto.AuthenticationRequest;
import com.trip.back.auth.dto.AuthenticationResult;
import com.trip.back.auth.dto.AuthenticationResultDto;
import com.trip.back.security.JwtAuthenticationToken;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin("*")
public class AuthController {
	
	 private final AuthenticationManager authenticationManager;
	 
	 @PostMapping
	  public ResponseEntity<AuthenticationResultDto> authentication(@RequestBody AuthenticationRequest authRequest) {
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

}
