package com.foodapp.dtos;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.foodapp.enums.UserRole;

import lombok.Data;

@Data
public class UserDto {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long userId;
	private String userName;
	private String email;
	
	private String password;
	
	private UserRole role;
}
