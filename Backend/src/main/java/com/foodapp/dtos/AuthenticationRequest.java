package com.foodapp.dtos;

import com.foodapp.enums.UserRole;

import lombok.Data;

@Data
public class AuthenticationRequest {

	private String email;
	private String password;
}
