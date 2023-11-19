package com.trip.back.exception;

import lombok.Getter;

@Getter
public enum ExceptionCode {
	MEMBER_EXISTS(100, "already member exists"),
	MEMBER_NOT_FOUND(101, "member not found"),
	CAN_NOT_SEND_MAIL(102, "too fast send email"),
	NO_SUCH_ALGORITHM(103, " no such algorithm"),
	TOKEN_NOT_FOUND(200, "token not found"),
	TOKEN_NOT_OBTAIN(201, "jwt obtain process error"),
	SEARCH_TYPE_NOT_FOUND(300, "search type not found"),
	UNAUTHORIZE(401, "credential exception");
	
	
	ExceptionCode(int code, String messsage) {
		this.code= code;
		this.message = message;
	}
	private int code;
	private String message;
	
	
}
