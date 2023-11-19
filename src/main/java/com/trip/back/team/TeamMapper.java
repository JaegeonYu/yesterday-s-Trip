package com.trip.back.team;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface TeamMapper {
	
	@Insert("insert into follow(account_id, team_id) values(#{accountId}, #{teamId})")
	void insertFollow(@Param("accountId")Long accountId, @Param("teamId")Long teamId);
	
	@Insert("INSERT INTO notification (account_id, title, content, create_at) " + 
			"SELECT f.id as account_id,  #{title}, #{content}, now() "
			+ "FROM follow f JOIN team t ON f.account_id = t.account_id" + 
			"WHERE t.id = #{teamId}")
	void insertNotification(@Param("teamId")Long teamId, Notification notification);
	
	// 팀 생성은 db에서만
	@Select("select * from notification n join accounts a n.account_id = a.id"
			+ "where a.id = #{accountId}")
	List<Notification> selectNotificationByAccountId(@Param("accountId") Long accountId);
	
	@Update("update notification set checked = true"
			+ "where id in"
			+ "<foreach collections='notifications' item='id' separator=',' open='(' close=')'>"
			+ "#{id}"
			+ "</foreach>")
	void readNotifications(@Param("notifications")List<Notification> notifications);
}
