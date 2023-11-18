package com.trip.back.review;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ReviewMapper {
	
	@Insert("insert into review(account_id, content_id, content, emotion, create_at) "
			+ "values(#{accountId}, #{contentId}, #{content}, #{emotion}, now())")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	public void insert(Review review);
	
	
	@Select("select r.id, a.nickname, r.content, r.emotion, r.create_at as createAt from review r join "
			+ "accounts a on r.account_id = a.id where r.content_id = #{id};")
	public List<ReviewSelectDto> selectById(@Param("id")Long contentId);
	
	@Update("update attraction_info set total_score = total_score + #{score} where content_id = #{attractionId}")
	void updateScore(@Param("score")Long score, @Param("attractionId")Long attractionId);
}
