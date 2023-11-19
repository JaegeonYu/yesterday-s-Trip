package com.trip.back.account.event;

import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.trip.back.account.RedisService;
import com.trip.back.mail.EmailMessage;
import com.trip.back.mail.EmailService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Transactional(readOnly = true)
@Async
@RequiredArgsConstructor
public class AccountventListener {
	private final EmailService emailService;
	private final static String domain = "yesterday's trip";
	
	@EventListener
	public void handlerPasswordEvent(FindEmailEvent emailEvent) {
		log.info("email : {} , pass {} ", emailEvent.getEmail(), emailEvent.getPassword());
		
		EmailMessage email = EmailMessage.builder()
		.subject(domain+" 임시비밀번호")
		.to(emailEvent.getEmail())
		.message("임시비밀번호  : " + emailEvent.getPassword()).build();
		
		emailService.sendEamil(email);
	}
	
	@EventListener
	public void handleVerificateEvent(VerificateEmailEvent emailEvent) {
		EmailMessage email = EmailMessage.builder()
		.subject(domain + "이메일 인증")
		.to(emailEvent.getEmail())
		.message("인증코드 : " + emailEvent.getCode())
		.build();
		
		emailService.sendEamil(email);
		
	}
}
