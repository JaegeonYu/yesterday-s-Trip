package com.trip.back.auth;

import lombok.Data;

@Data
public class AccessRefreshDto {
	private String apiToken;

	public AccessRefreshDto(String apiToken) {
		super();
		this.apiToken = apiToken;
	}
	
}
