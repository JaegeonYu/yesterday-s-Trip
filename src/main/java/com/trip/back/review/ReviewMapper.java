package com.trip.back.review;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReviewMapper {
	
	@Insert("insert into review(account_id, content_id, title, content, emotion) "
			+ "values(#{accountId}, #{contentId}, #{title}, #{content}, #{emotion})")
	public void insert(Review review);
}
