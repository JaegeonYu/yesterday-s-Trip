package com.trip.back.attraction;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class AttractionBestDto {
	
	private Double shortestDistance;
	private List<AttractionResDto> shortestPath;
	public AttractionBestDto(Double shortestDistance, List<AttractionResDto> shortestPath) {
		super();
		this.shortestDistance = shortestDistance;
		this.shortestPath = shortestPath;
	}
	
	
}
