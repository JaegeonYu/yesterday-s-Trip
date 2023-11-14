package com.trip.back.attraction;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttractionService {
	private final AttractionMapper attractionRepository;
	
	public List<AttractionInfo> selectByRegionAndContentType(int sido, int gugun, Long contentType){
		return attractionRepository.selectBySidoAndGugunAndContentType(sido, gugun, contentType);
	}
	public List<AttractionInfo> selectByRegion(int sido, int gugun){
		return attractionRepository.selectBySidoAndGugun(sido, gugun);
	}
	
	public List<AttractionInfo> selectBySido(int sido){
		return attractionRepository.selectBySido(sido);
	}
}
