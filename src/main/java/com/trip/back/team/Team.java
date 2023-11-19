package com.trip.back.team;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class Team {
	private Long id;
	private String name;
	private Long accountId; // manager
	
	@Builder
	public Team(String name, Long accountId) {
		super();
		this.name = name;
		this.accountId = accountId;
	}
	
	
}
