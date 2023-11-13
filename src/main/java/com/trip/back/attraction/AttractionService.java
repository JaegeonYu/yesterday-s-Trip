package com.trip.back.attraction;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttractionService {
	private final AttractionMapper attractionRepository;
	
	public List<AttractionInfo> selectByRegionAndContentType(int sido, int gugun, Long contentType){
		return attractionRepository.selectByRegionAndContentType(sido, gugun, contentType);
	}
}
