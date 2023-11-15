package com.trip.back.security;

import static com.google.common.base.Preconditions.checkArgument;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.trip.back.account.Account;
import com.trip.back.model.Id;



public class JwtAuthentication {
	  public final Id<Account, Long> id;

	  public final String name;

	  public final String email;

	  public JwtAuthentication(Long id, String name, String email) {
	    checkArgument(id != null, "id must be provided.");
	    checkArgument(name != null, "name must be provided.");
	    checkArgument(email != null, "email must be provided.");

	    this.id = Id.of(Account.class, id);
	    this.name = name;
	    this.email = email;
	  }

	  @Override
	  public String toString() {
	    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
	      .append("id", id)
	      .append("name" , name)
	      .append("email", email)
	      .toString();
	  }
}
