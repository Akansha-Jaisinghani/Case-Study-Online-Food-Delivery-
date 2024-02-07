package com.foodapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foodapp.entity.Dish;
import com.foodapp.entity.Restaurant;


@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
	Restaurant findByRestaurantName(String name);
	
	List<Restaurant> findAllByRestaurantNameContaining(String title);
}
