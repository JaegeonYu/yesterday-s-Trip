package com.trip.back.team;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trip.back.account.AccountMapper;
import com.trip.back.attraction.AttractionInfo;
import com.trip.back.attraction.AttractionMapper;
import com.trip.back.exception.ExceptionCode;
import com.trip.back.exception.ServiceException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TeamService {
	private final TeamMapper teamRepository;
	private final AccountMapper accountRepository;
	private final AttractionMapper attractionRepository;
	
	List<TeamFollowCheckDto> findAllTeam(Long accountId){
		List<Team> teams = teamRepository.findAllTeam();
		List<TeamFollowCheckDto> resultTeams = new ArrayList<TeamFollowCheckDto>();
		for(Team team : teams) {
			boolean follow = teamRepository.findFollowByAccountIdAndTeamId(team.getId(), accountId) > 0 ? true : false;
			resultTeams.add(new TeamFollowCheckDto(team, follow));
		}
		return resultTeams;
	}
	
	void addFollow(Long accountId, Long teamId) {
		if(teamRepository.findByTeamId(teamId) == null) throw new ServiceException(ExceptionCode.TEAM_NOT_FOUND);
		teamRepository.insertFollow(accountId, teamId);
	}
	
	void deleteFollow(Long accountId, Long teamId) {
		if(teamRepository.findByTeamId(teamId) == null) throw new ServiceException(ExceptionCode.TEAM_NOT_FOUND);
		teamRepository.deleteFollow(accountId, teamId);
	}
	
	
	void addAttraction(AttractionInfo attraction, Long teamId) {
		if(attractionRepository.selectByTitle(attraction.getTitle()) != null){
			log.info("이미 있는 관광지인데 안터짐 ?");
		}
		attractionRepository.insert(attraction);
		Team team = teamRepository.findByTeamId(teamId);
		if(teamRepository.findByTeamId(teamId) == null) throw new ServiceException(ExceptionCode.TEAM_NOT_FOUND);
		
		teamRepository.insertNotification(teamId, team.getName() + "에 새 소식이 있습니다.", attraction.getTitle());
		// TODO SSE
	
	}
	List<Team> findTeamsByAccountId(Long accountId){
		if(accountRepository.findById(accountId) == null )throw new ServiceException(ExceptionCode.MEMBER_NOT_FOUND);
		return teamRepository.findAllTeamByAccountId(accountId);
	}
	
	List<Notification> findNotReadNotificationByAccountId(Long accountId){
		if(accountRepository.findById(accountId) == null )throw new ServiceException(ExceptionCode.MEMBER_NOT_FOUND);
		return teamRepository.selectNotReadNotificationByAccountId(accountId);
	}
	
	List<Notification> findReadNotificationByAccountId(Long accountId){
		if(accountRepository.findById(accountId) == null )throw new ServiceException(ExceptionCode.MEMBER_NOT_FOUND);
		return teamRepository.selectReadNotificationByAccountId(accountId);
	}
	
	List<Notification> findAllNotificationByAccountId(Long accountId){
		if(accountRepository.findById(accountId) == null )throw new ServiceException(ExceptionCode.MEMBER_NOT_FOUND);
		return teamRepository.selectAllNotificationByAccountId(accountId);
	}
	
	void readNotifications(List<Notification> notifications) {
		teamRepository.readNotifications(notifications);
	}
	
	void readNotification(Long notificationId) {
		teamRepository.readNotification(notificationId);
	}
	
	
}
