package com.trip.back.review;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.trip.back.aop.LogExecutionTime;
import com.trip.back.security.JwtAuthentication;
import com.trip.back.sentiment.SentimentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/review")
@RequiredArgsConstructor
@Slf4j
public class ReviewController {
	private final ReviewService reviewService;
	private final SentimentService sentimentService;

	
	@PostMapping(value = "/{attractionId}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
	@LogExecutionTime
	public ResponseEntity<?> post(@PathVariable Long attractionId,
			@AuthenticationPrincipal JwtAuthentication authentication, @ModelAttribute Review review,
			@RequestPart(required = false) MultipartFile[] uploadImages) throws IOException{
		// review fk set
		
		review.setAccountId(authentication.id.value());
		review.setContentId(attractionId);
		
		// review emotion
		review.updateEmotion(sentimentService.makeEmotion(review.getContent()));
		// review save
		
		reviewService.save(review, uploadImages);
		

		log.info("after login auth : {}", authentication);
		return ResponseEntity.ok("h");
	}
	
	
	@GetMapping(value = "/list/{attractionId}")
	public ResponseEntity<?> post(@PathVariable Long attractionId,
			@AuthenticationPrincipal JwtAuthentication authentication) throws IOException{
		
		

		log.info("after login auth : {}", authentication);
		return ResponseEntity.ok(reviewService.selectByContentId(attractionId));
	}

}
