package com.trip.back.geo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

@Data
@AllArgsConstructor
@Getter
@ToString
public class Addresses {
	private String roadAddress;
	private String jibunAddress;
	private Double x;
	private Double y;
	
}
