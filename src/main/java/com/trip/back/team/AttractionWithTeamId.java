package com.trip.back.team;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AttractionWithTeamId {
	private Long contentId;
	private Long contentTypeId;
	private Long teamId;
	private int sidoCode;
	private int gugunCode;
	
	private String keyword;
	private String address;
	private String tel;
	private String zipcode;
	private String imageUrl;
	private Double latitude;
	private Double longitude;
	private String mlevel;
}
