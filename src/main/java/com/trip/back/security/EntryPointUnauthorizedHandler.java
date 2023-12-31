package com.trip.back.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EntryPointUnauthorizedHandler implements AuthenticationEntryPoint{
static ResponseEntity<?> E401 = new ResponseEntity<String>("unauthorized", HttpStatus.UNAUTHORIZED);	
	private final ObjectMapper objectMaper;
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		 response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		    response.setHeader("content-type", "application/json");
		    response.getWriter().write(objectMaper.writeValueAsString(E401));
		    response.getWriter().flush();
		    response.getWriter().close();
		
	}

}
