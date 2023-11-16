package com.trip.back.image;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ImageResultDto {
	private String uuid;
	private Long reviewId;
	private String fileURL;
	
	@Builder
	public ImageResultDto(Long reviewId, String uuid , String fileURL) {
		super();
		this.reviewId = reviewId;
		this.fileURL = fileURL;
		this.uuid = uuid;
	}
}
