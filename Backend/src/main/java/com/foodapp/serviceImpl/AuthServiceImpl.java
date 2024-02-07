package com.foodapp.serviceImpl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.foodapp.dtos.SignuUpRequest;
import com.foodapp.dtos.UserDto;
import com.foodapp.entity.User;
import com.foodapp.enums.UserRole;
import com.foodapp.exception.AlreadyPresentException;
import com.foodapp.repository.UserRepository;
import com.foodapp.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {
	
	@Autowired
	private Environment environment;


	private final UserRepository userRepository;
	
	public AuthServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@PostConstruct
	public void createAdminAccount() {
		User adminAccount = userRepository.findByRole(UserRole.ADMIN);
		if(adminAccount == null) {
			User user= new User();
			user.setName("Admin");
			user.setEmail(environment.getProperty("admin.email"));
			user.setPassword(new BCryptPasswordEncoder().encode(environment.getProperty("admin.password")));
			user.setRole(UserRole.ADMIN);
			userRepository.save(user);
		}
	}
	
	
	@Override
	public UserDto createUser(SignuUpRequest signuUpRequest) throws AlreadyPresentException {
		 if (userRepository.existsByEmail(signuUpRequest.getEmail())) {
		        // Email already exists, handle the error (throw exception, return an error response, etc.)
		        throw new AlreadyPresentException("Email is already in use");
		    }
		User user= new User();
		user.setName(signuUpRequest.getName());
		user.setEmail(signuUpRequest.getEmail());
		user.setPassword(new BCryptPasswordEncoder().encode(signuUpRequest.getPassword()));
		user.setRole(UserRole.CUSTOMER);
		User createdUser=userRepository.save(user);
		UserDto createdUserDto= new UserDto();
		createdUserDto.setUserId(createdUser.getUserId());
		createdUserDto.setUserName(createdUser.getName());
		createdUserDto.setEmail(createdUser.getEmail());
		createdUserDto.setRole(createdUser.getRole());
		return createdUserDto;	
	}
	
}
