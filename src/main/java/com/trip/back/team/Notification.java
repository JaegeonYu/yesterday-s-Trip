package com.trip.back.team;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Notification {
	private Long id;
	private Long accountId;
	private String title;
	private String keyword;
	private boolean checked;
	private LocalDateTime createAt;
	
	@Builder
	public Notification(Long accountId, String title, String keyword) {
		super();
		this.accountId = accountId;
		this.title = title;
		this.keyword = keyword;
	}
	
	@Builder
	public Notification(String title, String keyword) {
		super();
		this.title = title;
		this.keyword = keyword;
	}
	
	
	
}
