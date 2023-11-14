package com.trip.back.region;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ContentTypeDto {
	private Integer id;
	private String name;
	
	@Builder
	public ContentTypeDto(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
}
