package com.trip.back.auth.dto;

import static org.springframework.beans.BeanUtils.copyProperties;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trip.back.account.dto.AccountDto;


public class AuthenticationResultDto {

	private String apiToken;
	
	@JsonIgnore
	  private AccountDto account;

	  public AuthenticationResultDto(AuthenticationResult source) {
	    copyProperties(source, this);
	    System.out.println("source " + source);
	    this.account = new AccountDto(source.getAccount());
	    System.out.println("after copy" + this.account);
	  }

	  public String getApiToken() {
	    return apiToken;
	  }

	  public void setApiToken(String apiToken) {
	    this.apiToken = apiToken;
	  }

	  public AccountDto getAccount() {
	    return this.account;
	  }

	  public void setAccount(AccountDto account) {
	    this.account = account;
	  }

	  @Override
	  public String toString() {
	    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
	      .append("apiToken", apiToken)
	      .append("account", account)
	      .toString();
	  }
}
