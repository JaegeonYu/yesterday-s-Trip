package com.trip.back.account;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.trip.back.account.dto.JoinRequest;
import com.trip.back.account.dto.JoinResult;
import com.trip.back.dto.ApiResult;
import com.trip.back.security.JwtAuthentication;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.trip.back.dto.ApiResult.OK;

@RestController
@RequestMapping("api/account")
@RequiredArgsConstructor
@Slf4j
public class AccountController {

	  private final AccountService accountService;
	  
	  @PostMapping(path = "/join")
	  public ApiResult<JoinResult> join(@RequestBody JoinRequest joinRequest) {
	   int result = accountService.join(
	    	Account.builder().nickname(joinRequest.getNickname())
	    	.email(joinRequest.getEmail())
	    	.password(joinRequest.getPassword())
	    	.build()
	    );
	   
	    return OK(
	      new JoinResult("hello")
	    );
	  }
	  
	  
	  @GetMapping("/hello")
	  public ApiResult<String> hello(@AuthenticationPrincipal JwtAuthentication authentication){
		  log.info("get auth : {}", SecurityContextHolder.getContext().getAuthentication());
		  log.info("controller auth : {}", authentication);
		  return OK("hello");
	  }

}
