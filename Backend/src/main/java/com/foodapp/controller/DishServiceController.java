package com.foodapp.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.foodapp.dtos.DishDto;
import com.foodapp.entity.Dish;
import com.foodapp.exception.AlreadyPresentException;
import com.foodapp.exception.DishException;
import com.foodapp.exception.NotFoundException;
import com.foodapp.exception.NullFieldException;
import com.foodapp.service.DishService;

@RestController
@RequestMapping("/dish")
public class DishServiceController {

	@Autowired
	DishService dishService;
	
	
	@GetMapping("/view/{dishId}")
	public ResponseEntity<DishDto> getDish(@PathVariable Integer dishId) throws NullFieldException{
		DishDto dishDto = dishService.viewDish(dishId);
		return new ResponseEntity<DishDto>(dishDto, HttpStatus.ACCEPTED);
	}
	
//	@PostMapping("/add")
//	public ResponseEntity<Dish> addDish(@RequestBody Dish dish) throws AlreadyPresentException{
//		Dish newDish = dishService.addDish(dish);
//		return new ResponseEntity<Dish>(newDish, HttpStatus.CREATED);
//	}
	
	@PutMapping("/update/{dishId}")
	public ResponseEntity<DishDto> updateDish(@PathVariable("dishId") Integer dishId,@ModelAttribute DishDto item) throws NotFoundException,IOException{
		DishDto updatedDish = dishService.updateDish(dishId,item);
		return new ResponseEntity<DishDto>(updatedDish, HttpStatus.OK);
	}
	
	
	@DeleteMapping("/remove/{itemId}")
	public ResponseEntity<Dish> removeDish(@PathVariable("itemId") Integer itemId) throws DishException{
		Dish removedDish = dishService.removeDish(itemId);
		return new ResponseEntity<Dish>(removedDish, HttpStatus.ACCEPTED);
	}

	
//	@GetMapping("/viewall")
//	public ResponseEntity<List<DishDto>> getAllDishes() throws DishException{
//		List<DishDto> items = dishService.viewAllDishes();
//		return new ResponseEntity<List<DishDto>>(items, HttpStatus.OK);
//		
//	}
}
