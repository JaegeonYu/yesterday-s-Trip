package com.trip.back.mail;

import org.springframework.context.annotation.Profile;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@Profile("dev")
@RequiredArgsConstructor
public class RealEmailService implements EmailService{
	
	private final JavaMailSender javaMailSender;
	@Override
	public void sendEamil(EmailMessage emailMessage) {
		  SimpleMailMessage mailMessage= new SimpleMailMessage();
		  mailMessage.setTo(emailMessage.getTo());
		  mailMessage.setSubject(emailMessage.getSubject());
		  mailMessage.setText(emailMessage.getMessage());
		  
		  javaMailSender.send(mailMessage);
		
		log.info("real send email : {}" , emailMessage.getMessage());
		
	}

}
