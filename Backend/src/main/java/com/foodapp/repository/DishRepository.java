package com.foodapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foodapp.dtos.DishDto;
import com.foodapp.entity.Dish;
import com.foodapp.entity.Restaurant;

@Repository
public interface DishRepository extends JpaRepository<Dish, Integer>{

	Dish findByDishName(String name);
	
	List<Dish> findAllByRestaurantRestaurantId(Integer restaurantId);

	List<Dish> findAllByRestaurantRestaurantIdAndDishNameContaining(Integer restaurantId, String title);
	
}
