package com.trip.back.account.dto;

import javax.annotation.Nonnull;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinRequest {
	@NotBlank
	@Max(value = 16)
	private String nickname;
	
	@NotBlank
	@Max(value = 20)
	private String password;
	
	@NotBlank
	@Email
	private String email;

	protected JoinRequest() {
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("nickname", nickname)
				.append("password", password).append("email", email).toString();
	}
}
