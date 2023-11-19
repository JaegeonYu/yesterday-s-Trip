package com.trip.back.team;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Follow {
	private Long id;
	private Long teamId;
	private Long accountId; // followers
	
	@Builder
	public Follow(Long teamId, Long accountId) {
		super();
		this.teamId = teamId;
		this.accountId = accountId;
	}


	
	
}
