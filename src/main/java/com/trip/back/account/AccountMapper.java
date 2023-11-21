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

//    @Select("select a.id, a.email, a.password, a.nickname, a.email_at emailAt, r.role_id as roleId, r.account_id as roleAccountId, r.role"
//    		+ " from accounts a join roles r on a.id = r.account_id  where email = #{email} ")
//    @Results({
//    	@Result(property = "id", column="id"),
//    	@Result(property = "email", column="email"),
//    	@Result(property = "nickname", column="nickname"),
//    	@Result(property = "emailAt", column="email_at"),
//    	@Result(property = "roles", javaType = List.class, column = "role_id",
//    	many = @Many(select = "com.trip.back.account.AccountMapper.getRoles"))
//    })
    @Select("select  id, email, password,  nickname, email_at emailAt from accounts where email = #{email} ")
    Account findByEmail(String email);

    @Select("select  id, email, password,  nickname, email_at emailAt from accounts where nickname = #{nickname} ")
    Account findByNickname(String nickname);
    
    @Update("update accounts set password = #{password}, email_at = now() where id = #{id}")
    void updateRandomPassword(@Param("password")String password, @Param("id")Long accountId);
    
    @Update("update accounts set password = #{password} where id = #{id}")
    void updatePassword(@Param("password") String password, @Param("id")Long accountId);
    
    @Insert("insert into roles(account_id, role) values(#{accountId}, #{role})")
    int saveRole(Roles roles);
    
    @Select("select role_id as roleId, account_id roleAccountId, role from roles where account_id = #{accountId}")
    List<Roles> getRoles(@Param("accountId") Long accountId);
    
}
