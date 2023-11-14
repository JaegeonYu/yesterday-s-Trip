package com.trip.back.review;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.trip.back.sentiment.SentimentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {
	private final ReviewMapper reviewRepository;
	private final SentimentService sentimentService;
	
	public void save(Review review) {
		
		try {
			sentimentService.makeEmotion(review.getTitle()+ review.getContent());
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
