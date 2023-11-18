package com.trip.back.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
	
	@ExceptionHandler(ServiceException.class)
	public ResponseEntity<?> handleServiceException(ServiceException e){
		// 개발중 비즈니스 로직 상 오류 체크를 위한 반환, 추후 모호한 메세지 반환 예정
		return ResponseEntity.badRequest().body(e.getMessage());
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleValidatonException(MethodArgumentNotValidException e){
		String errorMessage = "Validation failed. " + e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
	}
}
