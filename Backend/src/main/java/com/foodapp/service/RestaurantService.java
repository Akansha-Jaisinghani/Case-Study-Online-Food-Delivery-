package com.foodapp.service;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import com.foodapp.dtos.DishDto;
import com.foodapp.dtos.RestaurantDto;
import com.foodapp.entity.Dish;
import com.foodapp.entity.Restaurant;
import com.foodapp.exception.AlreadyPresentException;
import com.foodapp.exception.EmptyFieldException;
import com.foodapp.exception.MandatoryFieldException;
import com.foodapp.exception.NotFoundException;

public interface RestaurantService {

    public Restaurant addRestaurant(RestaurantDto restaurantDto)throws AlreadyPresentException,IOException,MandatoryFieldException;
		
    public RestaurantDto updateRestaurant(Integer restaurantId,RestaurantDto restaurantDto) throws NotFoundException, IOException,AlreadyPresentException;
	
    public void removeRestaurant(Integer restaurantId) throws NotFoundException;
	
	public List<Restaurant> viewAllRestaurant()throws EmptyFieldException;
	
	public  List<DishDto> getDishesByRestaurant(Integer restaurantId) throws RuntimeException;
	
	public DishDto addDishByRestaurant(Integer restaurantId,DishDto dish) throws NotFoundException,AlreadyPresentException,IOException;

	public List<Restaurant> getAllRestaurantsByTitle(String title);

	public List<DishDto> getDishesByRestaurantAndTitle(Integer restaurantId,String title);

	public RestaurantDto getRestaurantById(Integer restaurantId);
}
