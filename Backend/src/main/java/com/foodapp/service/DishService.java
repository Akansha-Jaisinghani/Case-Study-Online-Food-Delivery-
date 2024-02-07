package com.foodapp.service;

import java.io.IOException;
import java.util.List;

import com.foodapp.dtos.DishDto;
import com.foodapp.entity.Dish;
import com.foodapp.exception.AlreadyPresentException;
import com.foodapp.exception.NotFoundException;
import com.foodapp.exception.NullFieldException;

public interface DishService {

//	public Dish addDish(Dish dish) throws AlreadyPresentException;
	
	public DishDto updateDish(Integer dishId,DishDto updatedDish) throws NotFoundException,IOException;
	
	public DishDto viewDish(Integer dishId) throws NullFieldException;
	
	public Dish removeDish(Integer DishId) throws NotFoundException;
	
//	public List<DishDto> viewAllDishes() throws NotFoundException;
}
