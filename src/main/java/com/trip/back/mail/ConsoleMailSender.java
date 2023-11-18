package com.trip.back.mail;

import java.io.InputStream;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Profile("local")
@Component
@Slf4j
public class ConsoleMailSender implements EmailService{

	@Override
	public void sendEamil(EmailMessage emailMessage) {
		log.info("send emal : {} ", emailMessage.getMessage());
		
	}


}
