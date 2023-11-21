package com.trip.back.team;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
public class TeamFollowCheckDto {
	private Long id;
	private String name;
	@Setter
	private boolean followed;
	
	@Builder
	public TeamFollowCheckDto(Long id, String name, boolean followed) {
		super();
		this.id = id;
		this.name = name;
		this.followed = followed;
	}

	public TeamFollowCheckDto(Team team, boolean followed) {
		super();
		this.id = team.getId();
		this.name = team.getName();
		this.followed = followed;
	}
	
	
}
