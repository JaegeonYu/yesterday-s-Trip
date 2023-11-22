package com.trip.back.team;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Notification {
	private Long id;
	private Long accountId;
	private String sidoName;
	private Long contentTypeId;
	private String keyword;
	private boolean checked;
	private LocalDateTime createAt;
	
	@Builder
	public Notification(Long accountId, String sidoName, String keyword) {
		super();
		this.accountId = accountId;
		this.sidoName = sidoName;
		this.keyword = keyword;
	}
	
	@Builder
	public Notification(String sidoName, String keyword) {
		super();
		this.sidoName = sidoName;
		this.keyword = keyword;
	}
	
	
	
}
