package com.trip.back.auth.dto;

import static com.google.common.base.Preconditions.checkArgument;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.trip.back.account.Account;



public class AuthenticationResult {

	  private final String apiToken;

	  private final Account account;

	  public AuthenticationResult(String apiToken, Account account) {
	    checkArgument(apiToken != null, "apiToken must be provided.");
	    checkArgument(account != null, "user must be provided.");

	    this.apiToken = apiToken;
	    this.account = account;
	  }

	  public String getApiToken() {
	    return apiToken;
	  }

	  public Account getAccount() {
	    return account;
	  }

	  @Override
	  public String toString() {
	    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
	      .append("apiToken", apiToken)
	      .append("account", account)
	      .toString();
	  }

	}
