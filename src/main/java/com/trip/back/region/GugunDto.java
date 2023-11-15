package com.trip.back.region;

import lombok.Data;
import lombok.Getter;

@Getter
public class GugunDto {
	private Integer id;
	private String name;
	
	public GugunDto(Gugun gugun) {
		super();
		this.id = gugun.getGugunCode();
		this.name = gugun.getName();
	}
}
