package com.trip.back.geo;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Data
@ToString
@Getter
public class GeoResponse {
	Addresses[] addresses;

}
