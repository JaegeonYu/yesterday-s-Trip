package com.trip.back.review;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import com.trip.back.image.ImageResultDto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString
public class Review {
	private Long id;
	private Long accountId;
	private Long contentId;
	private String content;
	private Emotion emotion;
	private Set<ImageResultDto> images;
	private LocalDateTime createAt;
	
	public Review( Long accountId, Long contentId, String content) {
		super();
		this.accountId = accountId;
		this.contentId = contentId;
		this.content = content;
		this.createAt = LocalDateTime.now();
	}
	
	public void updateEmotion(Emotion emotion) {
		this.emotion = emotion;
	}
}
