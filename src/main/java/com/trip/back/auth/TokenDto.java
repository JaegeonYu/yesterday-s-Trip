package com.trip.back.auth;

import lombok.Builder;
import lombok.Data;

@Data
public class TokenDto {
	private Long id;
	private String refresh;
	private Long accountId;
	
	@Builder
	public TokenDto(String refresh, Long accountId) {
		super();
		this.refresh = refresh;
		this.accountId = accountId;
	}
	
}
