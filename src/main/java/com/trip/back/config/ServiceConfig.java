package com.trip.back.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.spring.enjoytrip.security.Jwt;

@Configuration
public class ServiceConfig {
	
	  @Bean
	  public Jwt jwt(JwtTokenConfig jwtTokenConfig) {
	    return new Jwt(jwtTokenConfig.getIssuer(), jwtTokenConfig.getClientSecret(), jwtTokenConfig.getExpirySeconds());
	  }
}
