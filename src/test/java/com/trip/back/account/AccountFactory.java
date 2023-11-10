package com.trip.back.account;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AccountFactory {
	private final AccountMapper accountRepository;
	
	public Account createAccount(String nickname) {
		Account account = Account.builder().nickname(nickname)
		.email(nickname+"@email.com")
		.build();
		accountRepository.save(account);
		return account;
	}

}
