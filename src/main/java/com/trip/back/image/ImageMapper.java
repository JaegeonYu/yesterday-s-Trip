package com.trip.back.image;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ImageMapper {
	
	@Insert("insert into images(uuid, file_url, review_id) values (#{uuid}, #{fileURL}, #{reviewId})")
	void insert(ImageResultDto image);
	
	@Select("select uuid, review_id as reviewId, file_url as fileURL from images where review_id = #{reviewId}")
	List<ImageResultDto> selectByReviewId(Long reviewId);
}
