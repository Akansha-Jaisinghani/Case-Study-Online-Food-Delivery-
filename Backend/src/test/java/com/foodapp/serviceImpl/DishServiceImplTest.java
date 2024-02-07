package com.foodapp.serviceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;

import com.foodapp.dtos.DishDto;
import com.foodapp.dtos.RestaurantDto;
import com.foodapp.entity.Dish;
import com.foodapp.entity.Restaurant;
import com.foodapp.exception.EmptyFieldException;
import com.foodapp.exception.NotFoundException;
import com.foodapp.exception.NullFieldException;
import com.foodapp.repository.DishRepository;
import com.foodapp.serviceImpl.DishServiceImpl;

@ExtendWith(MockitoExtension.class)
public class DishServiceImplTest {

    @Mock
    private DishRepository dishRepository;

    @InjectMocks
    private DishServiceImpl dishService;

    @Test
    public void testUpdateDish() throws NotFoundException, IOException {
        // Mocking data
        int dishId = 1;
        DishDto dishDto = new DishDto();
        dishDto.setDishId(dishId);
        dishDto.setCost(122.0);
        dishDto.setDishName("UpdatedDish");
       // dishDto.setImg("Updated Image Content".getBytes());

        Dish existingDish = new Dish();
        existingDish.setDishId(dishId);
        existingDish.setDishName("OriginalDish");
        existingDish.setImg("Original Image Content".getBytes());

        // Mocking repository behavior
        when(dishRepository.findById(dishId)).thenReturn(Optional.of(existingDish));
        when(dishRepository.save(any())).thenAnswer(invocation -> {
            Dish savedDish = invocation.getArgument(0);
            return savedDish;
        });

        // Performing the test
        DishDto updatedDishDto = dishService.updateDish(dishId, dishDto);

        // Assertions
        assertNotNull(updatedDishDto);
        assertEquals(dishId, updatedDishDto.getDishId());
        assertEquals("UpdatedDish", updatedDishDto.getDishName());
       // assertArrayEquals("Updated Image Content".getBytes(), updatedDishDto.getImg());
    }

    @Test
    public void testViewDish() throws NullFieldException {
		Restaurant restaurant = new Restaurant();
		restaurant.setRestaurantName("UpdatedOasis");
		restaurant.setDescription("Updated Oasis Description");
    	
        // Mocking data
        int dishId = 1;
        Dish dish = new Dish();
        dish.setDishId(dishId);
        dish.setDishName("TestDish");
        dish.setRestaurant(restaurant);

        // Mocking repository behavior
        when(dishRepository.findById(dishId)).thenReturn(Optional.of(dish));

        // Performing the test
        DishDto viewedDishDto = dishService.viewDish(dishId);

        // Assertions
        assertNotNull(viewedDishDto);
        assertEquals(dishId, viewedDishDto.getDishId());
        assertEquals("TestDish", viewedDishDto.getDishName());
    }

    @Test
    public void testRemoveDish() throws NotFoundException {
        // Mocking data
        int dishId = 1;
        Dish dish = new Dish();
        dish.setDishId(dishId);
        dish.setDishName("TestDish");

        // Mocking repository behavior
        when(dishRepository.findById(dishId)).thenReturn(Optional.of(dish));

        // Performing the test
        Dish removedDish = dishService.removeDish(dishId);

        // Assertions
        assertNotNull(removedDish);
        assertEquals(dishId, removedDish.getDishId());
        assertEquals("TestDish", removedDish.getDishName());
        verify(dishRepository, times(1)).delete(dish);
    }

}
