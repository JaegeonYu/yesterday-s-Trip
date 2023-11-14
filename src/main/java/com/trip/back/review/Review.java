package com.trip.back.review;

import java.util.Set;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class Review {
	private Long id;
	private Long accountId;
	private Long contentId;
	private String title;
	private String content;
	private String emotion;
	private Set<Integer> images;
	
	public Review(Long accountId, Long contentId, String title, String content,	Set<Integer> images) {
		super();
		this.accountId = accountId;
		this.contentId = contentId;
		this.title = title;
		this.content = content;
		this.images = images;
	}
	
	
	
	

}
