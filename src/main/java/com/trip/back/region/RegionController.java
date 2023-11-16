package com.trip.back.region;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/region")
@RequiredArgsConstructor
public class RegionController {
	
	private final GugunMapper gugunRepository;
	private final SidoMapper sidoRepository;
	
	@GetMapping("/sido")
	public ResponseEntity<List<SidoDto>> sidoAll(){
		return ResponseEntity.ok(sidoRepository.selectAll().stream().map(SidoDto::new).collect(Collectors.toList()));
	}
	
	@GetMapping("/sido/{sidoCode}")
	public ResponseEntity<List<GugunDto>> gugunAll(@PathVariable String sidoCode){
		return ResponseEntity.ok(gugunRepository.selectBySidoCode(Integer.parseInt(sidoCode)).stream().map(GugunDto::new).collect(Collectors.toList()));
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
