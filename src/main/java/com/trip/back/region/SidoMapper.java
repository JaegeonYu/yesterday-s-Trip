package com.trip.back.region;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SidoMapper {
	
	@Select("select sido_code as sidoCode, sido_name as name from sido")
	public List<Sido> selectAll();
	
	@Select("select sido_name as name from sido where sido_code = #{sido}")
	public String selectByCode(@Param("sido") Integer sido);
}
