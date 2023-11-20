package com.trip.back.account;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.transaction.annotation.Transactional;

@Mapper
@Transactional(readOnly = true)
public interface AccountMapper {

    @Insert("insert into accounts(email, nickname, password, email_at) values (#{email} , #{nickname} , #{password}, now())")
    int save(Account account);

    @Select("select id, email, password, nickname, email_at emailDate from accounts where email = #{email} ")
    @Results({
    	@Result(property = "id", column="id"),
    	@Result(property = "email", column="email"),
    	@Result(property = "nickname", column="nickname"),
    	@Result(property = "emailAt", column="email_at"),
    	@Result(property = "roles", javaType = List.class, column = "id",
    	many = @Many(select = "getRoles"))
    })
    Account findByEmail(String email);

    @Select("select  id, email, password,  nickname, email_at emailDate from accounts where nickname = #{nickname} ")
    Account findByNickname(String nickname);
    
    @Update("update accounts set password = #{password}, email_at = now() where id = #{id}")
    void updatePassword(@Param("password")String password, @Param("id")Long accountId);
    
    @Insert("insert into roles(account_id, role) values(#{accountId}, #{role})")
    int save(Roles roles);
    
    
    
    
    @Select("select id, account_id accountId, role from roles where account_id = #{accountId}")
    List<Roles> getRoles(@Param("accountId") Long accountId);
    
}
