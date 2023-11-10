package com.trip.back.account;

import static org.springframework.beans.BeanUtils.copyProperties;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class AccountDto {
	private Long id;
	private String email;
	private String nickname;
	
	public AccountDto(Account account) {
		System.out.println("hello");
		System.out.println(account);
//		copyProperties(account, this);
		this.id = account.getId();
		this.email = account.getEmail();
		this.nickname = account.getNickname();
	}
	
	  @Override
	  public String toString() {
	    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
	      .append("id", id)
	      .append("email", email)
	      .append("nickname", nickname)
	      .toString();
	  }
}
