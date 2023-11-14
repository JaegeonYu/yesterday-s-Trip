package com.trip.back.region;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface GugunMapper {
	
	@Insert("insert into gugun(gugun_code, gugun_name, sido_code) values (#{gugunCode}, #{name}, #{sidoCode})")
	void insert(Gugun gugun);
	
	@Select("select gugun_code as gugunCode, sido_code as sidoCode, gugun_name as name "
			+ "from gugun where sido_code = #{sidoCode}")
	List<Gugun> selectBySidoCode(@Param("sidoCode")int sidoCode);
}
