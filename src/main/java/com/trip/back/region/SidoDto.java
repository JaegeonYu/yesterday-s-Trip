package com.trip.back.region;

import lombok.Getter;

@Getter
public class SidoDto {
	private Integer id;
	private String name;
	
	
	public SidoDto(Sido sido) {
		super();
		this.id = sido.getSidoCode();
		this.name = sido.getName();
	}
	
}
