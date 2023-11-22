package com.trip.back.review;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.Data;

@Data
public class ReviewSelectDto {
	private Long id;
	private String nickname;
	private String content;
	private Emotion emotion;
	private Set<String> imageURLs = new HashSet<String>();
	private LocalDateTime createAt;
}
