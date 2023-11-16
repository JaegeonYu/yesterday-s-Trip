package com.trip.back.auth.dto;

import static org.springframework.beans.BeanUtils.copyProperties;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trip.back.account.dto.AccountDto;

import lombok.Data;

@Data
public class AuthenticationResultDto {

	private String apiToken;
	private String refreshToken;
	
	@JsonIgnore
	  private AccountDto account;

	  public AuthenticationResultDto(AuthenticationResult source) {
	    copyProperties(source, this);
	    System.out.println("source " + source);
	    this.account = new AccountDto(source.getAccount());
	    System.out.println("after copy" + this.account);
	  }



	  @Override
	  public String toString() {
	    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
	      .append("apiToken", apiToken)
	      .append("account", account)
	      .toString();
	  }
}
