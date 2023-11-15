package com.trip.back.review;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface ReviewMapper {
	
	@Insert("insert into review(account_id, content_id, content, emotion, create_at) "
			+ "values(#{accountId}, #{contentId}, #{content}, #{emotion}, #{createAt})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	public void insert(Review review);
}
