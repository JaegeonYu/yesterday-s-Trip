package com.trip.back.region;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/region")
@CrossOrigin("*")
@RequiredArgsConstructor
public class RegionController {
	
	private final GugunMapper gugunRepository;
	private final SidoMapper sidoRepository;
	
	@GetMapping("/sido")
	public ResponseEntity<List<Sido>> sidoAll(){
		return ResponseEntity.ok(sidoRepository.selectAll());
	}
	
	@GetMapping("/sido/{sidoCode}")
	public ResponseEntity<List<Gugun>> gugunAll(@PathVariable int sidoCode){
		return ResponseEntity.ok(gugunRepository.selectBySidoCode(sidoCode));
	}
	
	@GetMapping("/contentType")
	public ResponseEntity<List<ContentTypeDto>> contentTypes(){
		List<ContentTypeDto> contentTypes = new ArrayList<>();
		for(ContentType value : ContentType.values()) {
			contentTypes.add(ContentTypeDto.builder().id(value.getContentTypeId()).name(value.getContentTypeName()).build());
		}
		
		return ResponseEntity.ok(contentTypes);
	}
	
	
}
