package com.foodapp.dtos;

import com.foodapp.enums.UserRole;

import lombok.Data;

@Data
public class AuthenticationResponse {
//    private final String token;
//
//    public AuthenticationResponse(String token) {
//        this.token = token;
//    }

	private String jwt;
	
	private UserRole userRole;
	
	private Long userId;
}