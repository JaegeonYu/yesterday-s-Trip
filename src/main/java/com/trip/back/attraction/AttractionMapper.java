package com.trip.back.attraction;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.transaction.annotation.Transactional;

@Mapper
public interface AttractionMapper {

	@Insert("insert into attraction_info(content_type_id,"
			+ "address, tel, zipcode, image_url, latitude, longitude, mlevel, title, sido_code, gugun_code, total_score)"
			+ " values( #{contentTypeId}, #{address}, #{tel}, #{zipcode}, "
			+ "#{imageUrl}, #{latitude}, #{longitude}, #{mlevel}, #{title}, #{sidoCode}, #{gugunCode}, 0)")
	void insert(AttractionInfo attractionInfo);

	@Select("select content_id as contentId, content_type_id as contentTypeId, sido_code as sidoCode, gugun_code as gugunCode,"
			+ "title, address, tel, zipcode, image_url as imageUrl, latitude, longitude, mlevel, total_score as totalScore, reviewCount"
			+ " from attraction_info where sido_code=#{sido} and gugun_code=#{gugun} and content_type_id=#{contentTypeId}"
			+ " join review r on r.content_id = attraction_info.content")
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
	
	
	@Select("select content_id as contentId, content_type_id as contentTypeId, sido_code as sidoCode, gugun_code as gugunCode,"
			+ "title, address, tel, zipcode, image_url as imageUrl, latitude, longitude, mlevel, "
			+ "total_score / (select count(*) from attraction_info a join review r on a.content_id = r.content_id where a.content_id = ai.content_id) as avgScore"
			+ " from attraction_info ai where ai.sido_code = #{sido} order by avgScore desc limit 3")
	List<AttractionResDto> selectBySidoBestScoreLimit3(@Param("sido") Integer sido);
	
}
