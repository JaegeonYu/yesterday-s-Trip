package com.trip.back.account;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.transaction.annotation.Transactional;

@Mapper
@Transactional(readOnly = true)
public interface AccountMapper {

    @Insert("insert into accounts(email, nickname, password, email_date) values (#{email} , #{nickname} , #{password}, now())")
    int save(Account account);

    @Select("select id, email, password, email_date emailDate from accounts where email = #{email} ")
    Account findByEmail(String email);

    @Select("select  id, email, password, email_date emailDate from accounts where nickname = #{nickname} ")
    Account findByNickname(String nickname);
    
    @Update("update accounts set password = #{password}, email_date = now() where id = #{id}")
    void updatePassword(@Param("password")String password, @Param("id")Long accountId);
    
    
}
