package com.foodapp.serviceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.foodapp.dtos.DishDto;
import com.foodapp.dtos.RestaurantDto;
import com.foodapp.entity.Dish;
import com.foodapp.entity.Restaurant;
import com.foodapp.exception.AlreadyPresentException;
import com.foodapp.exception.EmptyFieldException;
import com.foodapp.exception.NotFoundException;
import com.foodapp.repository.DishRepository;
import com.foodapp.repository.RestaurantRepository;

//@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
public class RestaurantServiceImplTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private DishRepository dishRepository;

    @Mock
    private Environment environment;

    @InjectMocks
    private RestaurantServiceImpl restaurantService;

    @Test
    public void testAddRestaurant() throws Exception {
        // Mocking the input RestaurantDto
        RestaurantDto restaurantDto = new RestaurantDto();
        restaurantDto.setRestaurantName("Oasis");
        restaurantDto.setDescription("Oasis Description");
        restaurantDto.setFoodAttachment(mockMultipartFile("application/pdf"));
        restaurantDto.setLicenseAttachment(mockMultipartFile("application/pdf"));

        // Mocking the service response
        Restaurant mockedRestaurant = new Restaurant();
        mockedRestaurant.setRestaurantName("Oasis");
        mockedRestaurant.setDescription("Oasis Description");

        when(restaurantRepository.findByRestaurantName(anyString())).thenReturn(null);
        when(restaurantRepository.save(any())).thenReturn(mockedRestaurant);
      
        // Performing the test
        Restaurant result = restaurantService.addRestaurant(restaurantDto);

        // Assertions
        assertNotNull(result);
        assertEquals("Oasis", result.getRestaurantName());
        assertEquals("Oasis Description", result.getDescription());

        // Verify that restaurantRepository.save was called
        verify(restaurantRepository, times(1)).save(any());
    }

    private MultipartFile mockMultipartFile(String contentType) throws IOException {
        return new MockMultipartFile(
                "file", "test.doc", contentType, "Spring Framework".getBytes());
    }
    
    
    @Test
    void testUpdateRestaurant() throws NotFoundException, IOException, AlreadyPresentException {
        // Assuming a restaurant with ID 1 exists in the database
        int restaurantId = 1;

        // Mocking the input RestaurantDto
        RestaurantDto restaurantDto = new RestaurantDto();
        restaurantDto.setRestaurantName("UpdatedOasis");
        restaurantDto.setDescription("Updated Oasis Description");
//        restaurantDto.setReturnedFoodAttachment(mockMultipartFile("application/pdf").getBytes());
//        restaurantDto.setReturnedLicenseAttachment(mockMultipartFile("application/pdf").getBytes());
//        restaurantDto.setFoodAttachment(mockMultipartFile("application/pdf"));
//        restaurantDto.setLicenseAttachment(mockMultipartFile("application/pdf"));

        // Mocking the service response
        Restaurant existingRestaurant = new Restaurant();
        existingRestaurant.setRestaurantName("Oasis");
        existingRestaurant.setDescription("Oasis Description");
        existingRestaurant.setFoodAttachment("Existing Food Attachment".getBytes());
        existingRestaurant.setLicenseAttachment("Existing License Attachment".getBytes());

        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(existingRestaurant));
        when(restaurantRepository.findByRestaurantName(anyString())).thenReturn(null);
        when(restaurantRepository.save(any())).thenReturn(existingRestaurant);

        // Performing the test
        RestaurantDto updatedRestaurantDto = restaurantService.updateRestaurant(restaurantId, restaurantDto);

        // Assertions
        assertNotNull(updatedRestaurantDto);
        assertEquals("UpdatedOasis", updatedRestaurantDto.getRestaurantName());
        assertEquals("Updated Oasis Description", updatedRestaurantDto.getDescription());

        // Verify that restaurantRepository.save was called
        verify(restaurantRepository, times(1)).save(any());
    }
    

    @Test
    public void testGetDishesByRestaurant() {
    	 Restaurant restaurant = new Restaurant();
    	 restaurant.setRestaurantName("Oasis");
    	 restaurant.setDescription("Oasis Description");
        // Mocking data
        int restaurantId = 44;
        Dish dish1 = new Dish();
        dish1.setDishId(1);
        dish1.setDishName("Dish1");
        dish1.setRestaurant(restaurant);
        dish1.getRestaurant().setRestaurantId(restaurantId);
        Dish dish2 = new Dish();
        dish2.setDishId(2);
        dish2.setDishName("Dish2");
        dish2.setRestaurant(restaurant);
        dish2.getRestaurant().setRestaurantId(restaurantId);
        List<Dish> dishes = List.of(dish1, dish2);

        // Mocking repository behavior
        when(dishRepository.findAllByRestaurantRestaurantId(restaurantId)).thenReturn(dishes);

        // Performing the test
        List<DishDto> result = restaurantService.getDishesByRestaurant(restaurantId);

        // Assertions
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Dish1", result.get(0).getDishName());
        assertEquals("Dish2", result.get(1).getDishName());
    }

    @Test
    public void testAddDishByRestaurant() throws NotFoundException, AlreadyPresentException, IOException {
        // Mocking data
        int restaurantId = 1;
        DishDto dishDto = new DishDto();
        dishDto.setDishName("NewDish");
        dishDto.setImg(mockMultipartFile("image/jpeg"));

        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantId(restaurantId);

        // Mocking repository behavior
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurant));
        when(dishRepository.findByDishName("NewDish")).thenReturn(null);
        when(dishRepository.save(any())).thenAnswer(invocation -> {
            Dish savedDish = invocation.getArgument(0);
            savedDish.setDishId(1); // Simulating the saved entity having an ID
            return savedDish;
        });

        // Performing the test
        DishDto result = restaurantService.addDishByRestaurant(restaurantId, dishDto);

        // Assertions
        assertNotNull(result);
        assertEquals(1, result.getDishId());
        assertArrayEquals(dishDto.getImg().getBytes(), result.getReturnedImg());
    }

    @Test
    public void testRemoveRestaurant() throws NotFoundException {
        // Mocking data
        int restaurantId = 2;
        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantId(restaurantId);

        // Mocking repository behavior
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurant));

        // Performing the test
        assertDoesNotThrow(() -> restaurantService.removeRestaurant(restaurantId));
    }

//    @Test
//    public void testRemoveRestaurantNotFoundException() {
//        // Mocking data
//        int restaurantId = 1;
//
//        // Mocking repository behavior
//        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.empty());
//
//        // Performing the test
//        assertThrows(NotFoundException.class, () -> restaurantService.removeRestaurant(restaurantId));
//    }

    @Test
    public void testViewAllRestaurant() throws EmptyFieldException {
        // Mocking data
        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(new Restaurant());
        restaurants.add(new Restaurant());

        // Mocking repository behavior
        when(restaurantRepository.findAll()).thenReturn(restaurants);

        // Performing the test
        List<Restaurant> result = restaurantService.viewAllRestaurant();

        // Assertions
        assertNotNull(result);
        assertEquals(2, result.size());
    }

//    @Test
//    public void testViewAllRestaurantEmptyFieldException() {
//        // Mocking repository behavior
//        when(restaurantRepository.findAll()).thenReturn(new ArrayList<>());
//
//        // Performing the test
//        assertThrows(EmptyFieldException.class, () -> restaurantService.viewAllRestaurant());
//    }

//    private byte[] mockMultipartFile(String contentType) throws IOException {
//        // Mocking a simple image file
//        return "Image Content".getBytes();
//    }
}