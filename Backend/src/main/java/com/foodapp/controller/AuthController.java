package com.foodapp.controller;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.foodapp.dtos.AuthenticationRequest;
import com.foodapp.dtos.AuthenticationResponse;
import com.foodapp.dtos.SignuUpRequest;
import com.foodapp.dtos.UserDto;
import com.foodapp.entity.User;
import com.foodapp.exception.AlreadyPresentException;
import com.foodapp.exception.ErrorDetails;
import com.foodapp.repository.UserRepository;
import com.foodapp.service.AuthService;
import com.foodapp.util.JWTUtil;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	
    private final AuthService authService;
    
    private final UserDetailsService userDetailsService;
    
    private final AuthenticationManager authenticationManager;
    
    private final UserRepository userRepository;
    
    private final JWTUtil jwtUtil;
	
	public AuthController(AuthService authService,AuthenticationManager authenticationManager, UserDetailsService userDetailsService, JWTUtil jwtUtil, UserRepository userRepository) {
		this.authService=authService;
		this.userDetailsService = userDetailsService;
		this.authenticationManager=authenticationManager;
		this.userRepository = userRepository;
		this.jwtUtil = jwtUtil;
	}
	
	
	@PostMapping("/signUp")
	public ResponseEntity<?> signupUser(@RequestBody SignuUpRequest signUpRequest) throws AlreadyPresentException {
		
		UserDto createdUserDto = authService.createUser(signUpRequest);
		
		if (createdUserDto == null) {
			ErrorDetails errorResponse = new ErrorDetails("User not Created, try again later");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}
		return new  ResponseEntity<>(createdUserDto,HttpStatus.CREATED);
		
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest,HttpServletResponse response) throws IOException {
		try {
			// Attempt to authenticate the user using provided credentials
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),authenticationRequest.getPassword()));
		} catch(BadCredentialsException bce) {
			//throw new BadCredentialsException("Incorrect username or password");
			 ErrorDetails errorResponse = new ErrorDetails("Incorrect username or password");
		     return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
		   
		}  catch(DisabledException disabledException) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND,"user not active");
			// Return null as there is no valid authentication response
			return null;
		}
		
		 // If authentication is successful, generate a JWT token
		final UserDetails userDetails=userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
	    final String jwt = jwtUtil.generateToken(userDetails.getUsername());
	    
	    // Retrieve additional user details from the repository
	    Optional<User> optionalUser = userRepository.findFirstByEmail(userDetails.getUsername());
	    
	    // Create an AuthenticationResponse object and populate it with JWT and user details
	    AuthenticationResponse authenticationResponse = new AuthenticationResponse();
	    if(optionalUser.isPresent()) {
	    	authenticationResponse.setJwt(jwt);
	    	authenticationResponse.setUserRole(optionalUser.get().getRole());
	    	authenticationResponse.setUserId(optionalUser.get().getUserId());
	    }
	    return ResponseEntity.ok(authenticationResponse);
	}
	
	
}
