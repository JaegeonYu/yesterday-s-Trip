package com.trip.back.auth;

import java.io.UnsupportedEncodingException;

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
import org.springframework.web.client.HttpClientErrorException.Unauthorized;

import com.trip.back.auth.dto.AuthenticationRequest;
import com.trip.back.auth.dto.AuthenticationResult;
import com.trip.back.auth.dto.AuthenticationResultDto;
import com.trip.back.exception.ExceptionCode;
import com.trip.back.exception.ServiceException;
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
	 private final TokenService tokenService;

	 
	 @PostMapping
	  public ResponseEntity<AuthenticationResultDto> authentication(@RequestBody @Valid AuthenticationRequest authRequest) {
		 log.info("authRequest : {}", authRequest.toString());
		 
		 tokenService.ifExistRefresh(authRequest.getPrincipal());
	    try {
	      JwtAuthenticationToken authToken = new JwtAuthenticationToken(authRequest.getPrincipal(), authRequest.getCredentials());
	      
	      Authentication authentication = authenticationManager.authenticate(authToken);
	      SecurityContextHolder.getContext().setAuthentication(authentication);
	      log.info("after Login detials : {}", authentication.getDetails().toString());
	      return ResponseEntity.ok(
	        new AuthenticationResultDto((AuthenticationResult)authentication.getDetails())
	      );
	    } catch (AuthenticationException e) {
	    	throw new ServiceException(ExceptionCode.UNAUTHORIZE);

	    }
	  }
	 
	 @PostMapping("/refreshToken")
	 public ResponseEntity<AccessRefreshDto> refresh(HttpServletRequest request){
		 log.info("check refresh access");
		 String token = request.getHeader("refreshToken");
		 
		 return ResponseEntity.ok(new AccessRefreshDto(tokenService.refreshAccess(token)));
	 }
	 
	 @GetMapping("/logout")
	 public ResponseEntity<String> refresh(@AuthenticationPrincipal JwtAuthentication authenticaiton){
		 
		 tokenService.delete(authenticaiton.id.value());
		 return ResponseEntity.ok("delete");
	 }
	 
	 
	 
	 
	 @GetMapping("/hello")
	 public ResponseEntity<?> hello(@AuthenticationPrincipal JwtAuthentication authenticaiton){
		 
		 return ResponseEntity.ok("auth check");
	 }

}
