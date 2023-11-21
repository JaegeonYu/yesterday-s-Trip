package com.trip.back.sse;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.Getter;

@Getter
public class AccountEmmiter extends SseEmitter{
	private Long accountId;

	public AccountEmmiter(Long accountId) {
		super();
		this.accountId = accountId;
	}
}
