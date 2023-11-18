package com.trip.back.account;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.trip.back.account.event.FindEmailEvent;
import com.trip.back.mail.EmailMessage;
import com.trip.back.mail.EmailService;

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
	
	private final ApplicationEventPublisher eventPublisher;
	
	public void updatePass(Account account) {
		if(account.canSendEmail()) { // 메일은 한시간에 하나만 가능
			 String password = RandomStringUtils.random(10, true, true);
				accountRepository.updatePassword(passwordEncoder.encode(password), account.getId());
				
				  
				eventPublisher.publishEvent(FindEmailEvent.builder()
						.password(password)
						.email(account.getEmail())
						.build());
		}
		throw new RuntimeException(); // TODO Exception
	}
}
