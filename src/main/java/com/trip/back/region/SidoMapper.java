package com.trip.back.region;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SidoMapper {
	
	@Select("select sido_code as sidoCode, sido_name as name from sido")
	public List<Sido> selectAll();
}
