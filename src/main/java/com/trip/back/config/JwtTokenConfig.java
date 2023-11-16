package com.trip.back.config;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties(prefix="jwt.token")
@Getter
@Setter
public class JwtTokenConfig {
	 private String header;

	  private String issuer;

	  private String clientSecret;

	  private int expirySeconds;
	  
	  private int refreshExpirySeconds;

	  @Override
	  public String toString() {
	    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
	      .append("header", header)
	      .append("issuer", issuer)
	      .append("clientSecret", clientSecret)
	      .append("expirySeconds", expirySeconds)
	      .toString();
	  }
}
