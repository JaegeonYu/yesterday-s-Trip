package com.trip.back.auth.dto;

import static com.google.common.base.Preconditions.checkArgument;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.trip.back.account.Account;

import lombok.Getter;


@Getter
public class AuthenticationResult {

	  private final String apiToken;
	  private final String refreshToken;
	  private final Account account;

	  public AuthenticationResult(String apiToken, String refreshToken, Account account) {
	    checkArgument(apiToken != null, "apiToken must be provided.");
	    checkArgument(account != null, "user must be provided.");

	    this.apiToken = apiToken;
	    this.refreshToken = refreshToken;
	    this.account = account;
	  }



	  @Override
	  public String toString() {
	    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
	      .append("apiToken", apiToken)
	      .append("refreshToekn", refreshToken)
	      .append("account", account)
	      .toString();
	  }

	}
