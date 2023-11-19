package com.trip.back.account.dto;

import javax.validation.constraints.Email;

import lombok.Data;

@Data
public class EmailDto {
	@Email
	private String email;
}
