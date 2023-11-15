package com.trip.back.review;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


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

	
}
