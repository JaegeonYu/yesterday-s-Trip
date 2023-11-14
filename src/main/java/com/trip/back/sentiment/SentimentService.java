package com.trip.back.sentiment;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SentimentService {
	@Value("${api.naver.id}")
	private String clientId;
	
	@Value("${api.naver.secret}")
	private String clientSecret;
	
	@Value("${api.naver.url}")
	private String url;
	
	private final ObjectMapper objectMapper;
	
	public String makeEmotion(String content) throws JsonProcessingException {
	
		HttpHeaders header = new HttpHeaders();
		header.add("X-NCP-APIGW-API-KEY-ID", clientId);
		header.add("X-NCP-APIGW-API-KEY", clientSecret);
		header.setContentType(MediaType.APPLICATION_JSON);
		
		String requestBody = objectMapper.writeValueAsString(new SentimentDto(content));
		
		RestTemplate request = new RestTemplate();
		HttpEntity<?> entity = new HttpEntity<>(requestBody, header);
		ResponseEntity<String> exchange = request.exchange(url , HttpMethod.POST, entity, String.class);
		log.info("sentiment api response : {}", exchange);
		
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		Map<String, Object> ha = objectMapper.readValue(exchange.getBody(), HashMap.class);
		Map<String, Object> ha1 = (Map<String, Object>) ha.get("document");
		log.info("response result : {}", ha1.get("sentiment"));
		return (String) ha1.get("sentiment");
	}
}
