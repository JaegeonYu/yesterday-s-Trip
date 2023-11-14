package com.trip.back.auth.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class AuthenticationRequest {
	@NotEmpty
	@Email
	private String principal;
	@NotEmpty

	private String credentials;

	protected AuthenticationRequest() {
	}

	public AuthenticationRequest(String principal, String credentials) {
		this.principal = principal;
		this.credentials = credentials;
	}

	public String getPrincipal() {
		return principal;
	}

	public String getCredentials() {
		return credentials;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("principal", principal)
				.append("credentials", credentials).toString();
	}
}
