package com.trip.back.account;

import java.util.UUID;

import javax.validation.Valid;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.trip.back.account.dto.JoinRequest;
import com.trip.back.account.dto.JoinResult;
import com.trip.back.security.JwtAuthentication;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;



@RestController
@RequestMapping("api/account")
@RequiredArgsConstructor
@Slf4j
public class AccountController {

	  private final AccountService accountService;
	  private final AccountMapper AccountMapper;
	 
	  
	  @PostMapping(path = "/join")
	  public ResponseEntity<?> join(@RequestBody @Valid JoinRequest joinRequest) {
		  log.info("checkout join {}" , joinRequest);
	   int result = accountService.join(
	    	Account.builder().nickname(joinRequest.getNickname())
	    	.email(joinRequest.getEmail())
	    	.password(joinRequest.getPassword())
	    	.build()
	    );
	   
	    return ResponseEntity.ok().build();
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
	  
	  @GetMapping("/findPass")
	  public ResponseEntity<?> findPass(@RequestParam String email){
		  // account password 변환 후 발송
		  
		  
		  Account account = accountService.findByEmail(email);
		  if(account == null) throw new RuntimeException(); // TODO exception
		  
		  accountService.updatePass(account);
		  return ResponseEntity.ok("temp pass update");
	  }

}
