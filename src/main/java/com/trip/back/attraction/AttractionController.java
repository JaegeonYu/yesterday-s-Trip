package com.trip.back.attraction;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/attraction")
@RequiredArgsConstructor
public class AttractionController {
	private final AttractionService attractionService;
	// 시, 구, 콘텐트 타입
	
	@GetMapping("/search/{sido}/{gugun}/{contentType}")
	public ResponseEntity<List<AttractionInfo>> search(@PathVariable int sido, @PathVariable int gugun, @PathVariable Long contentType){
		return ResponseEntity.ok(attractionService.selectByRegionAndContentType(sido, gugun, contentType));
	}
	
	
	
}
