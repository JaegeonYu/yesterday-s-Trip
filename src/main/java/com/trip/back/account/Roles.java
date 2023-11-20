package com.trip.back.account;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class Roles {
	private Long id;
	private Long accountId;
	private String role;
	
	@Builder
	public Roles(Long accountId, String role) {
		super();
		this.accountId = accountId;
		this.role = role;
	}
	
	
}
