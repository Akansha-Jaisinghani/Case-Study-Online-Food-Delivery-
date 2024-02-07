package com.foodapp.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foodapp.dtos.DishDto;
import com.foodapp.entity.Dish;
import com.foodapp.entity.Restaurant;
import com.foodapp.exception.AlreadyPresentException;
import com.foodapp.exception.EmptyFieldException;
import com.foodapp.exception.NotFoundException;
import com.foodapp.exception.NullFieldException;
import com.foodapp.repository.DishRepository;
import com.foodapp.service.DishService;

import io.jsonwebtoken.io.IOException;

@Service
public class DishServiceImpl implements DishService {
	private static final Logger logger = LoggerFactory.getLogger(DishServiceImpl.class.getName());

	@Autowired
	DishRepository dishRepository;

//	@Override
//	public Dish addDish(Dish dish) throws AlreadyPresentException {
//		logger.info("Adding a new dish: {}", dish.getDishName());
//		Optional<Dish> opt = Optional.of(dishRepository.findByDishName(dish.getDishName()));
//		if(opt.isPresent()) {
//			throw new AlreadyPresentException("Dish already exists..");
//		}else {
//			return dishRepository.save(dish);		
//		}
//	}

	@Override
	public DishDto updateDish(Integer dishId,DishDto dishDto) throws NotFoundException, java.io.IOException {
		logger.info("Updating book with ID: {}", dishDto.getDishId());
		Optional<Dish> opt = dishRepository.findById(dishId);
		if(opt.isPresent()) {
			Dish dish = opt.get();
			BeanUtils.copyProperties(dishDto, dish);
			if(dishDto.getImg() != null) {
				dish.setImg(dishDto.getImg().getBytes());
			}
			Dish updatedDish = dishRepository.save(dish);
			DishDto updatedDishDto = new DishDto();
			updatedDishDto.setDishId(updatedDish.getDishId());
			updatedDishDto.setDishName(updatedDish.getDishName());
			updatedDishDto.setCost(updatedDish.getCost());
			updatedDishDto.setReturnedImg(updatedDish.getImg());
			return updatedDishDto;
		}else {
			throw new NotFoundException("Dish not found");
		}
	}



	@Override
	public DishDto viewDish(Integer dishId) throws NullFieldException {
		logger.info("Searching for dishes with id: " + dishId);
		Optional<Dish> dish = dishRepository.findById(dishId);
		if(dish.isPresent()) {
			logger.info("Dish retrieved fo id " +  dishId + " is " +dish.get().getDishName());
			DishDto dishDto = dish.get().getDishDto();	
			return dishDto;
		}else {
			throw new NullFieldException("601", "name of the dish is null");
		}
	}



	@Override
	public Dish removeDish(Integer dishId) throws NotFoundException {
		logger.info("Deleting dish with ID: " + dishId);
		Dish dish = dishRepository.findById(dishId)
				.orElseThrow(() -> new NotFoundException(String.format("No Dish found with ID %d", dishId)));
		dishRepository.delete(dish);
		logger.info("Dish deleted with ID: {}", dishId);
		return dish;
	}



//	@Override
//	public List<DishDto> viewAllDishes() throws NotFoundException {
//				logger.info("Getting all Dishes");
//		List<DishDto> dishes = dishRepository.findAll().stream().map(Dish::getDishDto).collect(Collectors.toList());
//		if (dishes.isEmpty()) {
//			throw new EmptyFieldException("602", "No Dish exists..");
//		} else {
//			logger.info("Total dishes retrieved: {}", dishes.size());
//			return dishes;
//		}
//	}
}
