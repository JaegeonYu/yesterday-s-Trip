package com.trip.back.geo;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GeoResDto {
	private Double latitude;
	private Double longitude;
	
	@Builder
	public GeoResDto(Double latitude, Double longitude) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
	}
	

	
	
}
