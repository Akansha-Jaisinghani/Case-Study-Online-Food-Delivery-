package com.foodapp.service;

import com.foodapp.dtos.SignuUpRequest;
import com.foodapp.dtos.UserDto;
import com.foodapp.exception.AlreadyPresentException;

public interface AuthService {

	UserDto createUser(SignuUpRequest signuUpRequest) throws AlreadyPresentException  ;
}
