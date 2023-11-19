package com.trip.back.attraction;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface AttractionMapper {

	@Insert("insert into attraction_info(content_id, content_type_id,"
			+ "address, tel, zipcode, image_url, latitude, longitude, mlevel, title, sido_code, gugun_code, total_score)"
			+ " values(#{contentId}, #{contentTypeId}, #{address}, #{tel}, #{zipcode}, "
			+ "#{imageUrl}, #{latitude}, #{longitude}, #{mlevel}, #{title}, #{sidoCode}, #{gugunCode}, 0)")
	void insert(AttractionInfo attractionInfo);

	@Select("select content_id as contentId, content_type_id as contentTypeId, sido_code as sidoCode, gugun_code as gugunCode,"
			+ "title, address, tel, zipcode, image_url as imageUrl, latitude, longitude, mlevel, total_score as totalScore"
			+ " from attraction_info where sido_code=#{sido} and gugun_code=#{gugun} and content_type_id=#{contentTypeId}")
	List<AttractionInfo> selectBySidoAndGugunAndContentType(@Param("sido") int sido, @Param("gugun") int gugun,
			@Param("contentTypeId") Long contentTypeId);
	
	@Select("select content_id as contentId, content_type_id as contentTypeId, sido_code as sidoCode, gugun_code as gugunCode,"
			+ "title, address, tel, zipcode, image_url as imageUrl, latitude, longitude, mlevel, total_score as totalScore"
			+ "from attraction_info where sido_code=#{sido} and gugun_code=#{gugun}")
	List<AttractionInfo> selectBySidoAndGugun(@Param("sido") int sido, @Param("gugun") int gugun);
	
	@Select("select content_id as contentId, content_type_id as contentTypeId, sido_code as sidoCode, gugun_code as gugunCode,"
			+ "title, address, tel, zipcode, image_url as imageUrl, latitude, longitude, mlevel, total_score as totalScore"
			+ " from attraction_info where sido_code=#{sido}")
	List<AttractionInfo> selectBySido(@Param("sido") int sido);
	
	@Select("select content_id as contentId, content_type_id as contentTypeId, sido_code as sidoCode, gugun_code as gugunCode,"
			+ "title, address, tel, zipcode, image_url as imageUrl, latitude, longitude, mlevel, total_score as totalScore"
			+ " from attraction_infocontent_type_id=#{contentTypeId}")
	List<AttractionInfo> selectByContentType(@Param("contentTypeId") Long contentTypeId);
	
	@Select("select content_id as contentId, content_type_id as contentTypeId, sido_code as sidoCode, gugun_code as gugunCode,"
			+ "title, address, tel, zipcode, image_url as imageUrl, latitude, longitude, mlevel, total_score as totalScore"
			+ " from attraction_info where sido_code=#{sido} and content_type_id=#{contentTypeId}")
	List<AttractionInfo> selectBySidoAndContentType(@Param("sido") int sido, @Param("contentTypeId") Long contentTypeId);
	
	@Select("select content_id as contentId, content_type_id as contentTypeId, sido_code as sidoCode, gugun_code as gugunCode,"
			+ "title, address, tel, zipcode, image_url as imageUrl, latitude, longitude, mlevel ,total_score as totalScore "
			+ "from attraction_info where title like concat ('%', #{title}, '%')")
	List<AttractionInfo> selectByTitle(@Param("title") String title);

	
	@Select("select count(*) "
			+ "from attraction_info as at join review as rv on at.content_id = rv.content_id "
			+ "where at.content_id = #{attractionId}")
	Integer countReview(@Param("attractionId") Long attractionId); 
}
