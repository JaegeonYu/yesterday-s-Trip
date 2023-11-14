package com.trip.back.sentiment;

import lombok.Getter;

@Getter
public class SentimentDto {
	private String content;

	public SentimentDto(String content) {
		super();
		this.content = content;
	}

}
