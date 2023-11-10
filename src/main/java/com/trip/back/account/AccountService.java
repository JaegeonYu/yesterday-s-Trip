package com.trip.back.account;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.google.common.base.Preconditions.checkArgument;

import static java.util.regex.Pattern.matches;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import javax.management.RuntimeErrorException;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountService {
	
	private final AccountMapper accountRepository;
	private final PasswordEncoder passwordEncoder;
	
	public int join(Account account) {
		 checkArgument(isNotEmpty(account.getPassword()), "password must be provided.");
		    checkArgument(
		      account.getPassword().length() >= 4 &&  account.getPassword().length() <= 15,
		      "password length must be between 4 and 15 characters."
		    );

		 
		    account.passEncode(passwordEncoder.encode(account.getPassword()));
		return accountRepository.save(account);
	}
	
	public boolean existsByEmail(String email) {
		return accountRepository.existsByEmail(email) == 1 ? true : false;
	}
	
	public boolean existsByNickname(String nickname) {
		return accountRepository.existsByNickname(nickname) == 1 ? true : false;
	}
	
	public Account findByEmail(String email) {
		return  accountRepository.findByEmail(email);
	}
	

	public Account login(String email, String password) {
		checkArgument(password != null, "password must be provided.");
		Account account = findByEmail(email);
		if(account == null) {
			
			log.error("not exist email ");
			throw new RuntimeException();
		}
		
		account.login(passwordEncoder, password);
		
		
		return account;
	}
}
