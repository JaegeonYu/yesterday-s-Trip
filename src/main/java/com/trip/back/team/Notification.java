package com.trip.back.team;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Notification {
	private Long id;
	private Long accountId;
	private String title;
	private String content;
	private LocalDateTime creatAt;
	
	@Builder
	public Notification(Long accountId, String title, String content) {
		super();
		this.accountId = accountId;
		this.title = title;
		this.content = content;
	}
	
	@Builder
	public Notification(String title, String content) {
		super();
		this.title = title;
		this.content = content;
	}
	
	
	
}
