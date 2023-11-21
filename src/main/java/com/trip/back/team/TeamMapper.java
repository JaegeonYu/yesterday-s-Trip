package com.trip.back.team;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.trip.back.account.Account;

@Mapper
public interface TeamMapper {
	
	@Insert("insert into follow(account_id, team_id) values(#{accountId}, #{teamId})")
	void insertFollow(@Param("accountId")Long accountId, @Param("teamId")Long teamId);
	
	@Delete("delete from follow where account_id = #{accountId} and team_id = #{teamId}")
	void deleteFollow(@Param("accountId")Long accountId, @Param("teamId")Long teamId);
	
	@Insert("INSERT INTO notification (account_id, title, keyword, create_at, checked, content, content_type_id) " + 
			"SELECT f.account_id,  #{title}, #{keyword}, now(), false, #{content}, #{contentTypeId} "
			+ "FROM follow f JOIN team t ON f.team_id = t.id " + 
			"WHERE t.id = #{teamId}")
	void insertNotification(@Param("teamId")Long teamId, @Param("title") String title, 
			@Param("keyword") String keyword, @Param("content") String content, @Param("contentTypeId")Long contentTypeId);
	
	// 팀 생성은 db에서만
	@Select("select n.id, account_id accountId, title, keyword, create_at createAt, checked, content, content_type_id contentTypeId "
			+ "from notification n join accounts a on n.account_id = a.id "
			+ "where a.id = #{accountId} and checked = false")
	List<Notification> selectNotReadNotificationByAccountId(@Param("accountId") Long accountId);
	
	@Select("select n.id, account_id accountId, title, keyword, create_at createAt, checked, content, content_type_id contentTypeId "
			+ "from notification n join accounts a on n.account_id = a.id "
			+ "where a.id = #{accountId} and checked = true")
	List<Notification> selectReadNotificationByAccountId(@Param("accountId") Long accountId);
	
	@Select("select n.id, account_id accountId, title, keyword, create_at createAt, checked, content, content_type_id contentTypeId "
			+ "from notification n join accounts a on n.account_id = a.id "
			+ "where a.id = #{accountId}")
	List<Notification> selectAllNotificationByAccountId(@Param("accountId") Long accountId);
	
	@Update("update notification set checked = true"
			+ "where id in"
			+ "<foreach collections='notifications' item='id' separator=',' open='(' close=')'>"
			+ "#{id}"
			+ "</foreach>")
	void readNotifications(@Param("notifications")List<Notification> notifications);
	
	@Update("update notification set checked = true where id = #{id}")
	void readNotification(@Param("id")Long id);
	
	@Select("select id, name from team order by id")
	List<Team> findAllTeam();
	
	
	@Select("select id, name, account_id as accountId from team where id = #{teamId}")
	Team findByTeamId(@Param("teamId") Long teamId);
	
	@Select("select id, name, account_id as accountId from team where account_id = #{accountId}")
	List<Team> findAllTeamByAccountId(@Param("accountId") Long accountId);
	
	@Select("select account_id from follow f join team t on f.team_id = t.id where t.id = #{teamId}")
	@Result(property = "id", column = "account_id")
	List<Account> findFollowersByTeamId(@Param("teamId") Long teamId);
	
	
	@Select("select count(*) from team t join follow f on t.id = f.team_id where f.account_id = #{accountId} and t.id = #{teamId}")
	int findFollowByAccountIdAndTeamId(@Param("teamId") Long teamId, @Param("accountId") Long accountId);
}
