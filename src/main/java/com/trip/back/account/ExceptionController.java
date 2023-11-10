package com.trip.back.account;

import static com.trip.back.dto.ApiResult.ERROR;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.trip.back.dto.ApiResult;
@RestControllerAdvice
public class ExceptionController {
	
	@ExceptionHandler(Exception.class)
	public ApiResult<?> handleException(Exception e){
		return ERROR(e.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	
	
}
