package com.trip.back.attraction;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttractionService {
	private final AttractionMapper attractionRepository;
	
	public List<AttractionResDto> selectByRegionAndContentType(Integer sido, Integer gugun, Long contentType){
		return attractionRepository.selectBySidoAndGugunAndContentType(sido, gugun, contentType)
				.stream().map(entity -> {
					return AttractionResDto.builder().entity(entity)
					.count(findReviewCountById(entity)).build();
					
				}).collect(Collectors.toList());
	}
	public List<AttractionResDto> selectByRegion(Integer sido, Integer gugun){
		return attractionRepository.selectBySidoAndGugun(sido, gugun)
				.stream().map(entity -> {
					return AttractionResDto.builder().entity(entity)
					.count(findReviewCountById(entity)).build();
					
				}).collect(Collectors.toList());
	}
	
	public List<AttractionResDto> selectBySido(Integer sido){
		return attractionRepository.selectBySido(sido)
				.stream().map(entity -> {
					return AttractionResDto.builder().entity(entity)
					.count(findReviewCountById(entity)).build();
					
				}).collect(Collectors.toList());
	}
	
	public List<AttractionResDto> selectBySidoAndContentType(Integer sido,  Long contentType){
		return attractionRepository.selectBySidoAndContentType(sido, contentType)
				.stream().map(entity -> {
					return AttractionResDto.builder().entity(entity)
					.count(findReviewCountById(entity)).build();
					
				}).collect(Collectors.toList());
	}
	
	public List<AttractionResDto> selectByContentType(Long contentType){
		return attractionRepository.selectByContentType(contentType)
				.stream().map(entity -> {
					return AttractionResDto.builder().entity(entity)
					.count(findReviewCountById(entity)).build();
					
				}).collect(Collectors.toList());
	}
	
	public List<AttractionResDto> selectByTitle(String title){
		return attractionRepository.selectByTitle(title)
				.stream().map(entity -> {
					return AttractionResDto.builder().entity(entity)
					.count(findReviewCountById(entity)).build();
					
				}).collect(Collectors.toList());
	}
	
	private Integer findReviewCountById(AttractionInfo info) {
		return attractionRepository.countReview(info.getContentId());
	}
}
