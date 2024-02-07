package com.foodapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.foodapp.dtos.DishDto;
import com.foodapp.dtos.EmailNotification;
import com.foodapp.dtos.RestaurantDto;
import com.foodapp.dtos.UserDto;
import com.foodapp.entity.Dish;
import com.foodapp.entity.Restaurant;
import com.foodapp.exception.AlreadyPresentException;
import com.foodapp.exception.NotFoundException;
import com.foodapp.exception.NullFieldException;
import com.foodapp.service.RestaurantService;

import io.jsonwebtoken.io.IOException;

@RestController
@RequestMapping("/restaurant")
public class RestaurantServiceController {
	private static final Logger logger = LoggerFactory.getLogger(RestaurantServiceController.class);

	@Autowired
	private Environment environment;
	
	@Autowired
	RestaurantService restaurantService;
	
	@Autowired
    private AmqpTemplate amqpTemplate;
	
	//used	
	@PostMapping("/register")
	public ResponseEntity<Restaurant> registerResturant(@Valid @ModelAttribute RestaurantDto restaurantDto)  throws Exception{
		Restaurant newRestaurant = restaurantService.addRestaurant(restaurantDto);
		
		  // Send email notification
        EmailNotification emailNotification = new EmailNotification();
     
        emailNotification.setTo(environment.getProperty("admin.email"));
        emailNotification.setSubject("Restaurant "+restaurantDto.getRestaurantName().toUpperCase() + " Registration Successful");
        emailNotification.setMessage("Thank you for registering!");

        amqpTemplate.convertAndSend("email-exchange", "email.notification", emailNotification);

		return new ResponseEntity<Restaurant>(newRestaurant ,HttpStatus.OK);
	}
	

	//used
	@GetMapping("/view/{restaurantId}")
	public ResponseEntity<RestaurantDto> getRestaurantById(@PathVariable Integer restaurantId) throws NullFieldException{
		RestaurantDto restaurantDto = restaurantService.getRestaurantById(restaurantId);
		return new ResponseEntity<RestaurantDto>(restaurantDto, HttpStatus.ACCEPTED);
	}
	
	//used
	@PutMapping("/update/{restaurantId}")
	public ResponseEntity<RestaurantDto> updateRestaurant(@PathVariable("restaurantId") Integer restaurantId,@Valid @ModelAttribute RestaurantDto restaurantDto) throws Exception{	
		RestaurantDto updatedResturantDto=restaurantService.updateRestaurant(restaurantId,restaurantDto);	
		return new ResponseEntity<RestaurantDto>(updatedResturantDto,HttpStatus.ACCEPTED);
	}
	
	//used
	@DeleteMapping("/remove/{restaurantId}")
	public ResponseEntity<Void> deleteRestaurant(@PathVariable("restaurantId") Integer restaurantId) throws Exception{
		logger.info("Deleting restaurant with ID: " + restaurantId);
		restaurantService.removeRestaurant(restaurantId);
		EmailNotification emailNotification = new EmailNotification();
	       // emailNotification.setTo(restaurantDto.getEmail()); // replace with actual email field
	        emailNotification.setTo(environment.getProperty("admin.email"));
	        emailNotification.setSubject("Restaurant with id "+restaurantId+ " Deregistered Successfuly");
	        emailNotification.setMessage("Restaurant is Deregistered!");

	        amqpTemplate.convertAndSend("email-exchange", "email.notification", emailNotification);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	//used
	@GetMapping("/view")
	public ResponseEntity<List<Restaurant>> viewAllRestaurants() throws Exception{
		List<Restaurant> restaurant =restaurantService.viewAllRestaurant();	
    	return new ResponseEntity<List<Restaurant>>(restaurant ,HttpStatus.ACCEPTED);
	}
	
	//used
	@GetMapping("/{restaurantId}/viewDishes")
	public ResponseEntity<List<DishDto>> getDishesByRestaurant(@PathVariable("restaurantId") Integer restaurantId) throws Exception{
		//List<Dish> dishes = restaurantService.getDishesByRestaurant(restaurantId);
		List<DishDto> dishes = restaurantService.getDishesByRestaurant(restaurantId);
		return new ResponseEntity<List<DishDto>>(dishes ,HttpStatus.ACCEPTED);
	}
	
	//used
	@PostMapping("/{restaurantId}/addDish")
	public ResponseEntity<DishDto> addDish(@PathVariable("restaurantId") Integer restaurantId,@ModelAttribute DishDto dishDto) throws Exception{
		DishDto createdDishDto=restaurantService.addDishByRestaurant(restaurantId, dishDto);
		return new ResponseEntity<DishDto>(createdDishDto ,HttpStatus.CREATED);
	}
	
	//used
	@GetMapping("/restaurants/{title}")
	public ResponseEntity<List<Restaurant>> getRestaurantByTitle(@PathVariable String title) throws Exception{
		List<Restaurant> restaurants = restaurantService.getAllRestaurantsByTitle(title);
		return new ResponseEntity<List<Restaurant>>(restaurants ,HttpStatus.ACCEPTED);
	}
	
	//used
	@GetMapping("/{restaurantId}/dishes/{title}")
	public ResponseEntity<List<DishDto>> getDishesByRestaurantAndTitle(@PathVariable Integer restaurantId,@PathVariable String title) throws Exception{
		List<DishDto> dishes = restaurantService.getDishesByRestaurantAndTitle(restaurantId,title);
		return new ResponseEntity<List<DishDto>>(dishes ,HttpStatus.ACCEPTED);
	}
	
}
