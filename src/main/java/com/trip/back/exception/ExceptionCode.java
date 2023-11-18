package com.trip.back.exception;

import lombok.Getter;

@Getter
public enum ExceptionCode {
	MEMBER_EXISTS(100, "already member exists"),
	MEMBER_NOT_FOUND(101, "member not found"),
	CAN_NOT_SEND_MAIL(102, "too fast send email");
	
	ExceptionCode(int code, String messsage) {
		this.code= code;
		this.message = message;
	}
	private int code;
	private String message;
	
	
}
