package com.trip.back.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAccessDeniedHandler implements AccessDeniedHandler{
	static ResponseEntity<?> E403 = new ResponseEntity<String>("Authentication error (cause : forbidden)", HttpStatus.FORBIDDEN);
	
	private final ObjectMapper objectMaper;

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		response.setHeader("content-type", "application/json");
		response.getWriter().write(objectMaper.writeValueAsString(E403));
		response.getWriter().flush();
		response.getWriter().close();
	}

}
