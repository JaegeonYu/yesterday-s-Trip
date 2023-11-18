package com.trip.back.account;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface AccountMapper {

    @Insert("insert into accounts(email, nickname, password) values (#{email} , #{nickname} , #{password})")
    int save(Account account);

    @Select("select count(*) from accounts where email = #{email} ")
    int existsByEmail(String email);


    int existsByNickname(String nickname);

    @Select("select * from accounts where email = #{email} ")
    Account findByEmail(String email);

    @Select("select * from accounts where nickname = #{nickname} ")
    Account findByNickname(String nickname);
    
    @Update("update accounts set password = #{password} where id = #{id}")
    void updatePassword(@Param("password")String password, @Param("id")Long accountId);
}
