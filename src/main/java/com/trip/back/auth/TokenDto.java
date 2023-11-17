package com.trip.back.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TokenDto {
	private Long id;
	private String refreshToken;
	private Long accountId;
	
	@Builder
	public TokenDto(String refreshToken, Long accountId) {
		super();
		this.refreshToken = refreshToken;
		this.accountId = accountId;
	}
	
}
