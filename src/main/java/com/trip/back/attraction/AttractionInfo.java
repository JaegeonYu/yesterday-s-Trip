package com.trip.back.attraction;

import com.trip.back.team.AttractionWithContent;

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
	
	public AttractionInfo(AttractionWithContent att) {
		this.contentTypeId = att.getContentTypeId();
		this.sidoCode = att.getSidoCode();
		this.gugunCode = att.getGugunCode();
		this.title = att.getTitle();
		this.address = att.getAddress();
		this.tel = att.getTel();
		this.zipcode = att.getZipcode();
		this.imageUrl = att.getImageUrl();
		this.latitude = att.getLatitude();
		this.longitude = att.getLongitude();
		this.mlevel = att.getMlevel();
	}
}
