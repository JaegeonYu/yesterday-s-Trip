package com.trip.back.image;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class Image {
	private Long id;
	private Long reviewId;
	private String saveFile;
	
	@Builder
	public Image(Long reviewId, String saveFile) {
		super();
		this.reviewId = reviewId;
		this.saveFile = saveFile;
	}
}
