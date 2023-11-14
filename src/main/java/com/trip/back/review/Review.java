package com.trip.back.review;

import java.util.Set;

import com.trip.back.image.Image;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Review {
	private Long id;
	private Long accountId;
	private Long contentId;
	private String title;
	private String content;
	private String emotion;
	private Set<Image> images;
	
	public Review( Long accountId, Long contentId, String title, String content) {
		super();
		this.accountId = accountId;
		this.contentId = contentId;
		this.title = title;
		this.content = content;
	}
	
	public void updateEmotion(String emotion) {
		this.emotion = emotion;
	}
}
