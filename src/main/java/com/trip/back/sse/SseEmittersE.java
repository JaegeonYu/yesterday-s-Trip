package com.trip.back.sse;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.trip.back.team.Notification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class SseEmittersE {

	private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();
	private static final AtomicLong eventId = new AtomicLong();
	private static final Long DEFAULT_TIMEOUT = 120L * 1000 * 60;
	
	public boolean checkSse(Long accountId) {
		if(emitters.get(accountId) == null) return false;
		return true;
	}
	
	public SseEmitter add(Long accountId, List<Notification> notifications) {
		SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);
		emitters.put(accountId, emitter);
		
		 try {  
	            emitter.send(SseEmitter.event()  
	                    .name("connect")  
	                    .data("connected!"));  
	        } catch (IOException e) {  
	            throw new RuntimeException(e);  
	        }  
		
		makeLoginNotification(accountId, notifications);
		
		log.info("new emitter added: {}", emitter);
		log.info("emitter list size: {}", emitters.size());

		emitter.onCompletion(() -> {
			log.info("onCompletion callback");
			this.emitters.remove(accountId); // 만료되면 리스트에서 삭제
		});

		emitter.onTimeout(() -> {
			log.info("onTimeout callback");
			emitter.complete();
		});

		return emitter;
	}

	public void makeNotification(Long accountId, Long notificationId) {
		try {
			emitters.get(accountId)
			.send(SseEmitter.event()
					.name("notification")
					.data(String.valueOf(notificationId)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void makeLoginNotification(Long accountId, List<Notification> notifications) {
		for (int i = 0; i < notifications.size(); i++) {
			try {
				makeNotification(accountId, notifications.get(i).getId());
			} catch (Exception e) {
				throw new RuntimeException(e);
			}

		}
	}
}
