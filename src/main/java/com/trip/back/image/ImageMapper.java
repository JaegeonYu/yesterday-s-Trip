package com.trip.back.image;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ImageMapper {
	
	@Insert("insert into images(uuid, file_url, review_id) values (#{uuid}, #{fileURL}, #{reviewId})")
	void insert(ImageResultDto image);
}
