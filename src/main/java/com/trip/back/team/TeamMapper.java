package com.trip.back.team;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface TeamMapper {
	
	@Insert("insert into follow(account_id, team_id) values(#{accountId}, #{teamId})")
	void insertFollow(@Param("accountId")Long accountId, @Param("teamId")Long teamId);
	
	@Delete("delete from follow where account_id = #{accountId} and team_id = #{teamId}")
	void deleteFollow(@Param("accountId")Long accountId, @Param("teamId")Long teamId);
	
	@Insert("INSERT INTO notification (account_id, title, content, create_at, checked) " + 
			"SELECT f.account_id as account_id,  #{title}, #{content}, now(), false "
			+ "FROM follow f JOIN team t ON f.team_id = t.id" + 
			"WHERE t.id = #{teamId}")
	void insertNotification(@Param("teamId")Long teamId, Notification notification);
	
	// 팀 생성은 db에서만
	@Select("select * from notification n join accounts a n.account_id = a.id"
			+ "where a.id = #{accountId} and checked = false")
	List<Notification> selectNotReadNotificationByAccountId(@Param("accountId") Long accountId);
	
	@Select("select * from notification n join accounts a n.account_id = a.id"
			+ "where a.id = #{accountId} and checked = true")
	List<Notification> selectReadNotificationByAccountId(@Param("accountId") Long accountId);
	
	@Update("update notification set checked = true"
			+ "where id in"
			+ "<foreach collections='notifications' item='id' separator=',' open='(' close=')'>"
			+ "#{id}"
			+ "</foreach>")
	void readNotifications(@Param("notifications")List<Notification> notifications);
}
