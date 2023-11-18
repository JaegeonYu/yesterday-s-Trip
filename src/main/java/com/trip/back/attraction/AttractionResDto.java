package com.trip.back.attraction;

import lombok.Builder;
import lombok.Data;

@Data
public class AttractionResDto {
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
	private Double avgScore;
	
	@Builder
	public AttractionResDto(AttractionInfo entity, Integer count) {
		super();
		this.contentId = entity.getContentId();
		this.contentTypeId = entity.getContentTypeId();
		this.sidoCode = entity.getSidoCode();
		this.gugunCode = entity.getGugunCode();
		this.title = entity.getTitle();
		this.address = entity.getAddress();
		this.tel = entity.getTel();
		this.zipcode =entity.getZipcode();
		this.imageUrl = entity.getImageUrl();
		this.latitude = entity.getLatitude();
		this.longitude = entity.getLongitude();
		this.mlevel = entity.getMlevel();
		this.avgScore = entity.getAvgScore(count);
	}
	
	
}
