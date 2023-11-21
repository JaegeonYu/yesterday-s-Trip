package com.trip.back.sse;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class SseEmittersE {
	
	private final List<AccountEmmiter> emitters = new CopyOnWriteArrayList<>();
	
	public AccountEmmiter add(AccountEmmiter emitter, int notificationSize) {  
		
	   makeLoginNotification(emitter, notificationSize);
		
		this.emitters.add(emitter);  
	        log.info("new emitter added: {}", emitter);  
	        log.info("emitter list size: {}", emitters.size());  
	        
	        emitter.onCompletion(() -> {  
	            log.info("onCompletion callback");  
	            this.emitters.remove(emitter);    // 만료되면 리스트에서 삭제
	        });  
	        
	        emitter.onTimeout(() -> {  
	            log.info("onTimeout callback");  
	            emitter.complete();  
	        });  
	  
	        return emitter;  
	    }
	
	public void makeNotification(Long accountId) {
		emitters.forEach(emitter ->{
			try {
				if(accountId == emitter.getAccountId()) {
					emitter.send(SseEmitter.event()
							.name("notification")
							.data("새로운 알람"));
				}
			}catch(Exception e) {
				throw new RuntimeException(e);
			}
		});
	}
	
	public void makeLoginNotification(AccountEmmiter emitter, int notificationSize) {
			for(int i=0;i<notificationSize;i++) {
				try {
					emitter.send(SseEmitter.event()
							.name("notification")
							.data("새로운 알람"));
				}catch (Exception e) {
					throw new RuntimeException(e);
				}
				
			}
		}
}
