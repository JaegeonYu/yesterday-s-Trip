package com.trip.back.review;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trip.back.security.JwtAuthentication;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/review")
@RequiredArgsConstructor
public class ReviewController {
	private final ReviewService reviewService;
	
	@PostMapping("/{attractionId}")
	public ResponseEntity<?> post(@PathVariable Long attractionId, @AuthenticationPrincipal JwtAuthentication authentication, @RequestBody Review review){
		
		
		return ResponseEntity.ok("h");
	}
}
