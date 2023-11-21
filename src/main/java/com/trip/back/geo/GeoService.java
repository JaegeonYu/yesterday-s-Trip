package com.trip.back.geo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class GeoService {
	
	@Value("${api.naver.id}")
	private String clientId;
	
	@Value("${api.naver.secret}")
	private String clientSecret;
	
	@Value("${api.naver.map.url}")
	private String url;
	
	private final ObjectMapper objectMapper;
	
	public GeoResDto makePositon(String address) throws JsonProcessingException {
		
		HttpHeaders header = new HttpHeaders();
		header.add("X-NCP-APIGW-API-KEY-ID", clientId);
		header.add("X-NCP-APIGW-API-KEY", clientSecret);
		header.setContentType(MediaType.APPLICATION_JSON);
		
		log.info("geo url : {}", url);
		

		
		RestTemplate request = new RestTemplate();
		HttpEntity<?> entity = new HttpEntity<>(header);
		ResponseEntity<String> exchange = request.exchange(url+"?query="+address , HttpMethod.GET, entity, String.class);
		log.info("geo api response : {}", exchange);
		
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		GeoResponse response = objectMapper.readValue(exchange.getBody(), GeoResponse.class);
		log.info("response result : {}", response);
		// TODO emotion + score 반환
		// x == longitu , y = latitu
		log.info("positon : {} ,,{}" , response.getAddresses()[0].getX(), response.getAddresses()[0].getY());
		
		return GeoResDto.builder().longitude(response.getAddresses()[0].getX()).latitude(response.getAddresses()[0].getY()).build();
	}
	
}
