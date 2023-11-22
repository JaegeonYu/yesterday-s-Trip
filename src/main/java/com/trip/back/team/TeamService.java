package com.trip.back.team;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.trip.back.account.Account;
import com.trip.back.account.AccountMapper;
import com.trip.back.attraction.AttractionInfo;
import com.trip.back.attraction.AttractionMapper;
import com.trip.back.exception.ExceptionCode;
import com.trip.back.exception.ServiceException;
import com.trip.back.sse.SseEmittersE;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeamService {
	private final TeamMapper teamRepository;
	private final AccountMapper accountRepository;
	private final AttractionMapper attractionRepository;
	private final SseEmittersE accountEmitters;
	
	@Transactional
	List<TeamFollowCheckDto> findAllTeam(Long accountId){
		List<Team> teams = teamRepository.findAllTeam();
		List<TeamFollowCheckDto> resultTeams = new ArrayList<TeamFollowCheckDto>();
		for(Team team : teams) {
			boolean follow = teamRepository.findFollowByAccountIdAndTeamId(team.getId(), accountId) > 0 ? true : false;
			resultTeams.add(new TeamFollowCheckDto(team, follow));
		}
		return resultTeams;
	}
	
	@Transactional
	 void addFollow(Long accountId, Long teamId) {
		if(teamRepository.findByTeamId(teamId) == null) throw new ServiceException(ExceptionCode.TEAM_NOT_FOUND);
		teamRepository.insertFollow(accountId, teamId);
	}
	
	@Transactional
	void deleteFollow(Long accountId, Long teamId) {
		if(teamRepository.findByTeamId(teamId) == null) throw new ServiceException(ExceptionCode.TEAM_NOT_FOUND);
		teamRepository.deleteFollow(accountId, teamId);
	}
	
	@Transactional
	void addAttraction(AttractionWithTeamId attractionWithContent) {
//		if(!attractionRepository.selectByTitle(attractionWithContent.getTitle()).isEmpty()){
//			log.info("이미 있는 관광지인데 안터짐 ?");
//			throw new ServiceException(ExceptionCode.UNAUTHORIZE);
//		}
		attractionRepository.insert(new AttractionInfo(attractionWithContent));
		Team team = teamRepository.findByTeamId(attractionWithContent.getTeamId());
		if(teamRepository.findByTeamId(attractionWithContent.getTeamId()) == null) throw new ServiceException(ExceptionCode.TEAM_NOT_FOUND);
		teamRepository.insertNotification(attractionWithContent.getTeamId(), attractionWithContent.getSidoCode(), 
				attractionWithContent.getKeyword(),  attractionWithContent.getContentTypeId());
		// TODO SSE 해당 팀의 follower 들에게 해당하는 emitter 돌면서 알람보내기
		
		List<Account> accounts = teamRepository.findFollowersByTeamId(attractionWithContent.getTeamId());
		log.info("알람 보낼 accounts : {} ", accounts);
		for(Account account : accounts) {
			if(accountEmitters.checkSse(account.getId())) {
				 List <Notification> notis = findNotReadNotificationByAccountId(account.getId());
				 for(Notification no : notis) {
					 accountEmitters.makeNotification(account.getId(), no.getId());
				 }
				
			}
		}
	
	}
	
	@Transactional
	List<Team> findTeamsByAccountId(Long accountId){
		if(accountRepository.findById(accountId) == null )throw new ServiceException(ExceptionCode.MEMBER_NOT_FOUND);
		return teamRepository.findAllTeamByAccountId(accountId);
	}
	
	@Transactional
	public List<Notification> findNotReadNotificationByAccountId(Long accountId){
		if(accountRepository.findById(accountId) == null )throw new ServiceException(ExceptionCode.MEMBER_NOT_FOUND);
		List<Notification> notis = teamRepository.selectNotReadNotificationByAccountId(accountId);
		log.info("service : {}", notis);
		return notis;
	}
	
	@Transactional
	List<Notification> findReadNotificationByAccountId(Long accountId){
		if(accountRepository.findById(accountId) == null )throw new ServiceException(ExceptionCode.MEMBER_NOT_FOUND);
		List<Notification> notis = teamRepository.selectReadNotificationByAccountId(accountId);
		log.info("service : {}", notis);
		return notis;
	}
	
	@Transactional
	List<Notification> findAllNotificationByAccountId(Long accountId){
		if(accountRepository.findById(accountId) == null )throw new ServiceException(ExceptionCode.MEMBER_NOT_FOUND);
		List<Notification> notis = teamRepository.selectAllNotificationByAccountId(accountId);
		log.info("service : {}", notis);
		return notis;
	}
	
	@Transactional
	void readNotifications(List<Notification> notifications) {
		teamRepository.readNotifications(notifications);
	}
	
	@Transactional
	void readNotification(Long notificationId) {
		teamRepository.readNotification(notificationId);
	}
	
	
	SseEmitter addEmitters(Long accountId) {
		
		List<Notification> notifications = findNotReadNotificationByAccountId(accountId);
		return accountEmitters.add(accountId, notifications);
	}
	
}
