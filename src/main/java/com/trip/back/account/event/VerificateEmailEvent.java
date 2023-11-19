package com.trip.back.account.event;

import lombok.Builder;
import lombok.Getter;

@Getter
public class VerificateEmailEvent {
	private final String email;
	private final String code;
	
	@Builder
	public VerificateEmailEvent(String email, String code) {
		super();
		this.email = email;
		this.code = code;
	}
	
}
