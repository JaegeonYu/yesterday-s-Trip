package com.trip.back.account;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.trip.back.account.dto.JoinRequest;
import com.trip.back.account.dto.JoinResult;
import com.trip.back.security.JwtAuthentication;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;



@RestController
@RequestMapping("api/account")
@RequiredArgsConstructor
@CrossOrigin("*")
@Slf4j
public class AccountController {

	  private final AccountService accountService;
	  private final AccountMapper AccountMapper;
	  
	  @PostMapping(path = "/join")
	  public ResponseEntity<JoinResult> join(@RequestBody @Valid JoinRequest joinRequest) {
		  log.info("checkout join {}" , joinRequest);
	   int result = accountService.join(
	    	Account.builder().nickname(joinRequest.getNickname())
	    	.email(joinRequest.getEmail())
	    	.password(joinRequest.getPassword())
	    	.build()
	    );
	   
	    return ResponseEntity.ok(
	      new JoinResult("hello")
	    );
	  }
	  
	  
	  @GetMapping("/hello")
	  public ResponseEntity<String> hello(@AuthenticationPrincipal JwtAuthentication authentication){
		  log.info("get auth : {}", SecurityContextHolder.getContext().getAuthentication());
		  log.info("controller auth : {}", authentication);
		  return ResponseEntity.ok("hello");
	  }
	  
	  @GetMapping("/check/email/{email}")
	  public ResponseEntity<Boolean> checkEmail(@PathVariable String email){
		  if(AccountMapper.existsByEmail(email)== 0)return ResponseEntity.ok(false);
		  return ResponseEntity.ok(true);
	  }
	  
	  @GetMapping("/check/nickname/{nickname}")
	  public ResponseEntity<Boolean> checkNickname(@PathVariable String nickname){
		  if(AccountMapper.existsByNickname(nickname)== 0)return ResponseEntity.ok(false);
		  return ResponseEntity.ok(true);
	  }

}
