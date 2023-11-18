package com.trip.back.account;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trip.back.account.dto.JoinRequest;

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
	public ResponseEntity join(@RequestBody @Valid JoinRequest joinRequest) {
		log.info("checkout join {}", joinRequest);
		accountService.join(Account.builder().nickname(joinRequest.getNickname()).email(joinRequest.getEmail())
				.password(joinRequest.getPassword()).build());

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/check/email/{email}")
	public ResponseEntity<Boolean> checkEmail(@PathVariable String email) {
		if (AccountMapper.existsByEmail(email) == 0)
			return ResponseEntity.ok(false);
		return ResponseEntity.ok(true);
	}

	@GetMapping("/check/nickname/{nickname}")
	public ResponseEntity<Boolean> checkNickname(@PathVariable String nickname) {
		if (AccountMapper.existsByNickname(nickname) == 0)
			return ResponseEntity.ok(false);
		return ResponseEntity.ok(true);
	}

	@GetMapping("/findPass")
	public ResponseEntity findPass(@RequestParam String email) {
		accountService.updatePass(email);

		return new ResponseEntity<>(HttpStatus.OK);
	}

}
