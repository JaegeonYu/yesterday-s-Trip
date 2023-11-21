package com.trip.back.team;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AttractionWithContent {
	private Long contentId;
	private Long contentTypeId;
	private Long teamId;
	private String content;
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
}
