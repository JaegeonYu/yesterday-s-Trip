package com.trip.back.region;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GugunMapper {
	
	@Insert("insert into gugun(gugun_code, gugun_name, sido_code) values (#{gugunCode}, #{name}, #{sidoCode})")
	void insert(Gugun gugun);
}
