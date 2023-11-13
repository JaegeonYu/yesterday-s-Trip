package com.trip.back.region;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class Gugun {
	private int gugunCode;
	private String name;
	private int sidoCode;
	
	@Builder
	public Gugun(int gugunCode, String name, int sidoCode) {
		super();
		this.gugunCode = gugunCode;
		this.name = name;
		this.sidoCode = sidoCode;
	}
	
	
}
