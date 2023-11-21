package com.trip.back.team;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trip.back.security.JwtAuthentication;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/notification")
@RequiredArgsConstructor
public class NotificationController {
	private final TeamService teamService;
	
	@GetMapping
	public ResponseEntity<List<Notification>> allNotification(@AuthenticationPrincipal JwtAuthentication authenticaton){
		return ResponseEntity.ok(teamService.findAllNotificationByAccountId(authenticaton.id.value()));
	}
	
	@GetMapping("/read")
	public ResponseEntity<List<Notification>> readNotification(@AuthenticationPrincipal JwtAuthentication authenticaton){
		return ResponseEntity.ok(teamService.findReadNotificationByAccountId(authenticaton.id.value()));
	}
	
	@GetMapping("/not-read")
	public ResponseEntity<List<Notification>> notReadNotification(@AuthenticationPrincipal JwtAuthentication authenticaton){
		return ResponseEntity.ok(teamService.findNotReadNotificationByAccountId(authenticaton.id.value()));
	}
	
	@GetMapping("/read/{notificationId}")
	public ResponseEntity readUpdateNotification(@AuthenticationPrincipal JwtAuthentication authenticaton, @PathVariable Long notificationId){
		teamService.readNotification(notificationId);
		return new ResponseEntity(HttpStatus.OK);
	}
	
}
