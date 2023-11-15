package com.trip.back.account;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;
@RestControllerAdvice
@Slf4j
public class ExceptionController {
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleException(Exception e){
		log.info("handler check :");
		return ResponseEntity.badRequest().body(e.getMessage());
	}
}
