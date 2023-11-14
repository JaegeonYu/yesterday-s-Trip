package com.trip.back.attraction;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttractionService {
	private final AttractionMapper attractionRepository;
	
	public List<AttractionInfo> selectByRegionAndContentType(Integer sido, Integer gugun, Long contentType){
		return attractionRepository.selectBySidoAndGugunAndContentType(sido, gugun, contentType);
	}
	public List<AttractionInfo> selectByRegion(Integer sido, Integer gugun){
		return attractionRepository.selectBySidoAndGugun(sido, gugun);
	}
	
	public List<AttractionInfo> selectBySido(Integer sido){
		return attractionRepository.selectBySido(sido);
	}
	
	public List<AttractionInfo> selectBySidoAndContentType(Integer sido,  Long contentType){
		return attractionRepository.selectBySidoAndContentType(sido, contentType);
	}
	
	public List<AttractionInfo> selectByContentType(Long contentType){
		return attractionRepository.selectByContentType(contentType);
	}

}
