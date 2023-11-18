package com.trip.back.account.event;

import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PasswordEventListener {
	private final JavaMailSender mailSender;
	
	@EventListener
	public void handlerPasswordEvent(FindEmailEvent emailEvent) {
		log.info("email : {} , pass {} ", emailEvent.getEmail(), emailEvent.getPassword());
		
//		  SimpleMailMessage mailMessage= new SimpleMailMessage();
//		  mailMessage.setTo(email);
//		  mailMessage.setSubject("yesterday's Trip, 임시 비밀번호");
//		  mailMessage.setText("임시 비밀번호 : " + tempPass);
//		  
//		  mailSender.send(mailMessage);
	}
}
