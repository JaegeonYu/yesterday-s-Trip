package com.trip.back.account.event;

import org.springframework.context.ApplicationEvent;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FindEmailEvent {
	private String email;
	private String password;
	
	@Builder
	public FindEmailEvent(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}
	
}
