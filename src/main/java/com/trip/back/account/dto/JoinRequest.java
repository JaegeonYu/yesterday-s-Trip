package com.trip.back.account.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinRequest {
	private String nickname;

	  private String password;

	  private String email;

	  protected JoinRequest() {}

	  @Override
	  public String toString() {
	    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
	      .append("nickname", nickname)
	      .append("password", password)
	      .append("email", email)
	      .toString();
	  }
}
