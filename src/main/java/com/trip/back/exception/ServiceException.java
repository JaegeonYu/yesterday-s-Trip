package com.trip.back.exception;

import lombok.Getter;

@Getter
public class ServiceException extends RuntimeException{
	private int code;

	
	public ServiceException(ExceptionCode exception) {
		super(exception.getMessage());
		this.code = exception.getCode();
	}
	
	
}
