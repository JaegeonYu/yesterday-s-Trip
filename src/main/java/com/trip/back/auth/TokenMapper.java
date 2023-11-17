package com.trip.back.auth;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TokenMapper {
	
	@Insert("insert into tokens(refreshToken, account_id) values(#{refreshToken}, #{accountId})")
	void insert(TokenDto token);
	
	@Delete("delete from tokens where account_id = #{id}")
	void delete(@Param("id")Long accountId);
	
	@Select("select id, refreshToken, account_id as accountId from tokens where account_id = #{id}")
	TokenDto findByAccountId(@Param("id")Long accountId);
	
}
