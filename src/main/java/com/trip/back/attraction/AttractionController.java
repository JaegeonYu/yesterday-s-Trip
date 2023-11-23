package com.trip.back.attraction;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trip.back.exception.ExceptionCode;
import com.trip.back.exception.ServiceException;
import com.trip.back.region.SidoDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/attraction")
@RequiredArgsConstructor
public class AttractionController {
	private final AttractionService attractionService;
	private final AttractionMapper attractionRepository;
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
			throw new ServiceException(ExceptionCode.SEARCH_TYPE_NOT_FOUND);
		}
	}
	
	@GetMapping("/{keyword}")
	public ResponseEntity<List<AttractionResDto>> keword(@PathVariable String keyword){
		return ResponseEntity.ok(attractionService.selectByTitle(keyword));
	}
	
	@GetMapping("/best/sido/{sidoCode}")
	public ResponseEntity<AttractionBestDto> best(@PathVariable Integer sidoCode){
		return ResponseEntity.ok(attractionService.selectBestBySido(sidoCode));
	}
	
	@GetMapping("/best")
	public ResponseEntity<List<SidoDto>> best(){
		return ResponseEntity.ok(attractionService.selectBest3Region());
	}
	
	
}
