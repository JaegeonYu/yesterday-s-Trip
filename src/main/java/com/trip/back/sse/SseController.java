package com.trip.back.sse;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.trip.back.security.JwtAuthentication;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/sse")
public class SseController {
	  private final SseEmitters sseEmitters;  

	    @GetMapping(value = "/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)  
	    public ResponseEntity<SseEmitter> connect(HttpServletRequest request, @AuthenticationPrincipal JwtAuthentication authenticaiton) {  
	    	log.info("connect auth : {}", authenticaiton);
	        SseEmitter emitter = new SseEmitter();  
	        sseEmitters.add(emitter);
	        try {  
	            emitter.send(SseEmitter.event()  
	                    .name("connect")  
	                    .data("connected!"));  
	        } catch (IOException e) {  
	            throw new RuntimeException(e);  
	        }  
	        return ResponseEntity.ok(emitter);  
	    }
	    
	    @PostMapping("/count")  
	    public ResponseEntity<Void> count() {  
	        sseEmitters.count();  
	        return ResponseEntity.ok().build();  
	    }  
}
