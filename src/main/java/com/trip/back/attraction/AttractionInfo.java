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
	
	@Builder
	public AttractionInfo(Long contentId, Long contentTypeId, int sidoCode, int gugunCode, String title, String address,
			String tel, String zipcode, String imageUrl, Double latitude, Double longitude, String mlevel) {
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
	}
	

}
