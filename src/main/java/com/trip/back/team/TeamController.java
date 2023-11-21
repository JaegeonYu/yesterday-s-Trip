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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.trip.back.attraction.AttractionInfo;
import com.trip.back.geo.GeoService;
import com.trip.back.security.JwtAuthentication;

import io.lettuce.core.GeoSearch;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/team")
@RequiredArgsConstructor
@Slf4j
public class TeamController {
	private final TeamService teamService;
	private final GeoService geoSerivce;
	
	@GetMapping
	public ResponseEntity findTeam(@AuthenticationPrincipal JwtAuthentication authentication){
		return ResponseEntity.ok(teamService.findAllTeam(authentication.id.value()));
	}
	
	@GetMapping("/{teamId}")
	public ResponseEntity followTeam(@AuthenticationPrincipal JwtAuthentication authentication,
			@PathVariable Long teamId) {
		log.info("follow teamID : {} , accountId : {}", authentication.id.value(), teamId);
		teamService.addFollow(authentication.id.value(), teamId);
		return new ResponseEntity(HttpStatus.OK);
	}
	
	@DeleteMapping("/{teamId}")
	public ResponseEntity unfollowTeam(@AuthenticationPrincipal JwtAuthentication authentication,
			@PathVariable Long teamId) {
		log.info("un follow teamID : {} , accountId : {}", authentication.id.value(), teamId);
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
	
	@GetMapping("/find-position")
	@Secured("ROLE_ADMIN")
	public ResponseEntity checkPosition(@RequestParam String address) throws JsonProcessingException {
		geoSerivce.makePositon(address);
		return new ResponseEntity(HttpStatus.OK);
	}
}
