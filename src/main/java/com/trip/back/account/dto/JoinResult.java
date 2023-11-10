package com.trip.back.account.dto;

import static com.google.common.base.Preconditions.checkArgument;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
public class JoinResult {
	  private final String apiToken;


	  public JoinResult(String apiToken) {
	    checkArgument(apiToken != null, "apiToken must be provided.");

	    this.apiToken = apiToken;

	  }

	  public String getApiToken() {
	    return apiToken;
	  }



	  @Override
	  public String toString() {
	    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
	      .append("apiToken", apiToken)
	      .toString();
	  }
}
