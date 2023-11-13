package com.trip.back.region;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class Sido {
	private int sidoCode;
	private String name;
	
	@Builder
	public Sido(int sidoCode, String name) {
		super();
		this.sidoCode = sidoCode;
		this.name = name;
	}
}
