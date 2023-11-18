package com.trip.back.attraction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.management.RuntimeErrorException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/attraction")
@RequiredArgsConstructor
public class AttractionController {
	private final AttractionService attractionService;
	// 시, 구, 콘텐트 타입
	
	@GetMapping("/search")
	public ResponseEntity<List<AttractionResDto>> search(@RequestParam(required = false) Integer sido,
			@RequestParam(required = false) Integer gugun, @RequestParam(required = false) Long contentType){
		
		if(sido != null && gugun != null && contentType != null) {
			return ResponseEntity.ok(attractionService.selectByRegionAndContentType(sido, gugun, contentType));
		}else if(sido != null && gugun != null) {
			return ResponseEntity.ok(attractionService.selectByRegion(sido, gugun));
			
		}else if(sido != null && contentType != null) {
			return ResponseEntity.ok(attractionService.selectBySidoAndContentType(sido, contentType));
		}else if(contentType != null) {
			return ResponseEntity.ok(attractionService.selectByContentType(contentType));
		}else if(sido != null) {
			return ResponseEntity.ok(attractionService.selectBySido(sido));
		}else {
			throw new RuntimeException();
		}
	}
	
	@GetMapping("/{keyword}")
	public ResponseEntity<List<AttractionResDto>> keword(@PathVariable String keyword){
		return ResponseEntity.ok(attractionService.selectByTitle(keyword));
	}
	
	
	
}
