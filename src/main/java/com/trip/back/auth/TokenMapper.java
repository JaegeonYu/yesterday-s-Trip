package com.trip.back.auth;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TokenMapper {
	
	@Insert("insert into tokens(refresh, account_id) values(#{refresh}, #{accountId})")
	void insert(TokenDto token);
	
	@Delete("delete from tokens where id = #{id}")
	void delete(@Param("id")Long tokenId);
	
	@Select("select id, refresh, account_id as accountId from tokens where account_id = #{id}")
	TokenDto findByAccountId(@Param("id")Long accountId);
	
}
