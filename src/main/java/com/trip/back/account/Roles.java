package com.trip.back.account;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
public class Roles {
	private Long roleId;
	private Long roleAccountId;
	private String role;
	
	@Builder
	public Roles(Long roleAccountId, String role) {
		super();
		this.roleAccountId = roleAccountId;
		this.role = role;
	}
}
