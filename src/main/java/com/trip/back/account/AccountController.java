package com.trip.back.account;

import javax.validation.Valid;
import javax.validation.constraints.Email;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trip.back.account.dto.JoinRequest;
import com.trip.back.account.dto.PasswordRequest;
import com.trip.back.aop.LogExecutionTime;
import com.trip.back.security.JwtAuthentication;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/account")
@RequiredArgsConstructor
@Slf4j
public class AccountController {

	private final AccountService accountService;
	@PostMapping(path = "/join")
	public ResponseEntity join(@RequestBody @Valid JoinRequest joinRequest) {
		log.info("checkout join {}", joinRequest);
		accountService.join(Account.builder().nickname(joinRequest.getNickname()).email(joinRequest.getEmail())
				.password(joinRequest.getPassword()).build());

		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@GetMapping("/email/verification-request")
	@LogExecutionTime
	public ResponseEntity sendEmail(@RequestParam @Email String email) {
		
		accountService.sendCodeToEmail(email);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("/email/verification")
	public ResponseEntity verificateEmail(@RequestParam @Email String email, @RequestParam String authCode) {
		log.info("controller authCode : {}", authCode);
		return ResponseEntity.ok(accountService.verifiedCode(email, authCode));
	}
	
	@GetMapping("/check/email/{email}")
	public ResponseEntity<Boolean> checkEmail(@PathVariable @Email
			String email) {
		if (accountService.findByEmail(email) == null)return ResponseEntity.ok(false);
		return ResponseEntity.ok(true);
	}

	@GetMapping("/check/nickname/{nickname}")
	public ResponseEntity<Boolean> checkNickname(@PathVariable String nickname) {
		if (accountService.findByNickname(nickname)==null)return ResponseEntity.ok(false);
		return ResponseEntity.ok(true);
	}

	@GetMapping("/find-pass")
	public ResponseEntity findPass(@RequestParam @Email String email) {
		accountService.missPass(email);

		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("/check-pass")
	public ResponseEntity checkPass(@AuthenticationPrincipal JwtAuthentication authentication, @RequestParam String password) {
		return ResponseEntity.ok(accountService.checkPass(authentication.email, password));
	}
	
	@PostMapping("/change-pass")
	public ResponseEntity changePass(@AuthenticationPrincipal JwtAuthentication authentication, @RequestBody PasswordRequest passwordRequest) {
		log.info("email : {}  pass : {}" , authentication.email, passwordRequest.getPassword());
		accountService.changePass(authentication.email, passwordRequest.getPassword());
		
		return  new ResponseEntity<>(HttpStatus.OK);
	}
	
}
