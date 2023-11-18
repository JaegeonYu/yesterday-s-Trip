package com.trip.back.attraction;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class AttractionInfo {
	private Long contentId;
	private Long contentTypeId;
	private int sidoCode;
	private int gugunCode;
	
	private String title;
	private String address;
	private String tel;
	private String zipcode;
	private String imageUrl;
	private Double latitude;
	private Double longitude;
	private String mlevel;
	private Long totalScore;
	
	@Builder
	public AttractionInfo(Long contentId, Long contentTypeId, int sidoCode, int gugunCode, String title, String address,
			String tel, String zipcode, String imageUrl, Double latitude, Double longitude, String mlevel, Long totalScore) {
		super();
		this.contentId = contentId;
		this.contentTypeId = contentTypeId;
		this.sidoCode = sidoCode;
		this.gugunCode = gugunCode;
		this.title = title;
		this.address = address;
		this.tel = tel;
		this.zipcode = zipcode;
		this.imageUrl = imageUrl;
		this.latitude = latitude;
		this.longitude = longitude;
		this.mlevel = mlevel;
		this.totalScore = totalScore;
	}
	
	public Double getAvgScore(Integer count) {
		if(count == 0)return 0d;
		return Double.valueOf(Math.round((totalScore / count) * 100) / 100);
	}

}
