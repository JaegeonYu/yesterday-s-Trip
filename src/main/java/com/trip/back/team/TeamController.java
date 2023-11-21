package com.trip.back.team;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trip.back.attraction.AttractionInfo;
import com.trip.back.security.JwtAuthentication;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/team")
@RequiredArgsConstructor
public class TeamController {
	private final TeamService teamService;
	
	@GetMapping
	public ResponseEntity findTeam(@AuthenticationPrincipal JwtAuthentication authentication){
		return ResponseEntity.ok(teamService.findAllTeam(authentication.id.value()));
	}
	
	@GetMapping("/{teamId}")
	public ResponseEntity followTeam(@AuthenticationPrincipal JwtAuthentication authentication,
			@PathVariable Long teamId) {
		teamService.addFollow(authentication.id.value(), teamId);
		return new ResponseEntity(HttpStatus.OK);
	}
	
	@DeleteMapping("/{teamId}")
	public ResponseEntity unfollowTeam(@AuthenticationPrincipal JwtAuthentication authentication,
			@PathVariable Long teamId) {
		teamService.deleteFollow(authentication.id.value(), teamId);
		return new ResponseEntity(HttpStatus.OK);
	}
	
	@GetMapping("/teams")
	@Secured("ROLE_ADMIN")
	public ResponseEntity getTeamsByAccountId(@AuthenticationPrincipal JwtAuthentication authentication) {
		return ResponseEntity.ok(teamService.findTeamsByAccountId(authentication.id.value()));
	}
	
	@PostMapping("/attraction")
	@Secured("ROLE_ADMIN")
	public ResponseEntity addAttraction(@AuthenticationPrincipal JwtAuthentication authentication,
			@RequestBody AttractionInfo attraction, @RequestParam Long teamId) {
		teamService.addAttraction(attraction, teamId);
		
		return new ResponseEntity(HttpStatus.OK);
	}
}
