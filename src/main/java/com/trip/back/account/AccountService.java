package com.trip.back.account;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trip.back.account.event.FindEmailEvent;
import com.trip.back.account.event.VerificateEmailEvent;
import com.trip.back.exception.ExceptionCode;
import com.trip.back.exception.ServiceException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class AccountService {
	private static final String AUTH_CODE_PREFIX = "AuthCode ";
	private long authCodeExpirationMillis = 600000;
	private final RedisService redisService;
	private final AccountMapper accountRepository;
	private final PasswordEncoder passwordEncoder;

	public int join(Account account) {
		if (findByEmail(account.getEmail()) != null || findByNickname(account.getNickname()) != null)
			throw new ServiceException(ExceptionCode.MEMBER_EXISTS);
		checkArgument(isNotEmpty(account.getPassword()), "password must be provided.");
		checkArgument(account.getPassword().length() >= 4 && account.getPassword().length() <= 15,
				"password length must be between 4 and 15 characters.");

		account.passEncode(passwordEncoder.encode(account.getPassword()));
		return accountRepository.save(account);
	}



	public Account findByEmail(String email) {
		return accountRepository.findByEmail(email);
	}
	
	public Account findByNickname(String nickname) {
		return accountRepository.findByNickname(nickname);
	}

	public Account login(String email, String password) {
		checkArgument(password != null, "password must be provided.");
		Account account = findByEmail(email);
		if (account == null) {

			log.error("not exist email ");
			throw new ServiceException(ExceptionCode.MEMBER_NOT_FOUND);
		}

		account.login(passwordEncoder, password);

		return account;
	}

	private final ApplicationEventPublisher eventPublisher;

	public void updatePass(String email) {

		Account account = accountRepository.findByEmail(email);
		if (account == null)
			throw new ServiceException(ExceptionCode.MEMBER_NOT_FOUND);
//		if (account.canSendEmail()) { // 메일은 한시간에 하나만 가능
//			
//		}
		String password = RandomStringUtils.random(10, true, true);
		accountRepository.updatePassword(passwordEncoder.encode(password), account.getId());

		eventPublisher.publishEvent(FindEmailEvent.builder().password(password).email(account.getEmail()).build());

		throw new ServiceException(ExceptionCode.CAN_NOT_SEND_MAIL);
	}
	
	public void sendCodeToEmail(String email) {
		 if(findByEmail(email) != null)throw new ServiceException(ExceptionCode.MEMBER_EXISTS);
		 
		String authCode = createCode();
		redisService.setData(AUTH_CODE_PREFIX + email,
                authCode, authCodeExpirationMillis);
		
		eventPublisher.publishEvent(VerificateEmailEvent.builder()
				.email(email)
				.code(authCode)
				.build());
	}
	
	 private String createCode() {
	        int lenth = 6;
	        try {
	            Random random = SecureRandom.getInstanceStrong();
	            StringBuilder builder = new StringBuilder();
	            for (int i = 0; i < lenth; i++) {
	                builder.append(random.nextInt(10));
	            }
	            return builder.toString();
	        } catch (NoSuchAlgorithmException e) {
	            log.debug("MemberService.createCode() exception occur");
	            throw new ServiceException(ExceptionCode.NO_SUCH_ALGORITHM);
	        }
	    }
	 
	 public boolean verifiedCode(String email, String authCode) {
	        if(findByEmail(email) != null)throw new ServiceException(ExceptionCode.MEMBER_EXISTS);
	        String redisAuthCode = redisService.getData(AUTH_CODE_PREFIX + email);
	        log.info("email : {} verficode : {}", email, redisAuthCode);
	       return redisAuthCode != null && redisAuthCode.equals(authCode);

	        
	    }
}
