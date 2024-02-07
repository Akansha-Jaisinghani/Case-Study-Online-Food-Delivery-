package com.foodapp.serviceImpl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.foodapp.dtos.DishDto;
import com.foodapp.dtos.RestaurantDto;
import com.foodapp.entity.Dish;
import com.foodapp.entity.Restaurant;
import com.foodapp.exception.AlreadyPresentException;
import com.foodapp.exception.EmptyFieldException;
import com.foodapp.exception.MandatoryFieldException;
import com.foodapp.exception.NotFoundException;
import com.foodapp.exception.NullFieldException;
import com.foodapp.repository.DishRepository;
import com.foodapp.repository.RestaurantRepository;
import com.foodapp.service.DishService;
import com.foodapp.service.RestaurantService;

@Service
public class RestaurantServiceImpl implements RestaurantService {
	private static final Logger logger = LoggerFactory.getLogger(RestaurantServiceImpl.class.getName());

	@Autowired
	RestaurantRepository restaurantRepository;

	@Autowired
	DishRepository dishRepository;

	private static final Set<String> ALLOWED_CONTENT_TYPES = new HashSet<>(Arrays.asList("application/pdf", // PDF files
			"application/msword", // Microsoft Word files
			"application/vnd.openxmlformats-officedocument.wordprocessingml.document" // Word (docx) files
	));

	@Override
	public Restaurant addRestaurant(RestaurantDto restaurantDto)
			throws AlreadyPresentException, IOException, MandatoryFieldException {
		logger.info("Adding new Restaurant: " + restaurantDto.getRestaurantName());
		Optional<Restaurant> restName = Optional
				.ofNullable(restaurantRepository.findByRestaurantName(restaurantDto.getRestaurantName()));
		if (restName.isPresent()) {
			throw new AlreadyPresentException("Restaurant already exists");
		}
		if (restaurantDto.getFoodAttachment() == null || restaurantDto.getLicenseAttachment() == null) {
			throw new MandatoryFieldException("foodAndSafetyLicense Attachment is Mandatory");
		} else {
			Restaurant newRestaurant = new Restaurant();
			newRestaurant.setRestaurantName(restaurantDto.getRestaurantName());
			newRestaurant.setDescription(restaurantDto.getDescription());
			newRestaurant.setFoodAttachment(validateAndSaveAttachment(restaurantDto.getFoodAttachment()));
			newRestaurant.setLicenseAttachment(validateAndSaveAttachment(restaurantDto.getLicenseAttachment()));
			return restaurantRepository.save(newRestaurant);
		}
	}

	private byte[] validateAndSaveAttachment(MultipartFile attachment) throws IOException {
		// Validate content type
		if (!ALLOWED_CONTENT_TYPES.contains(attachment.getContentType())) {
			throw new IllegalArgumentException("Invalid content type for attachment.");
		}

		// Save attachment logic (you may want to save it to a specific directory or
		// storage service)
		// For simplicity, we are just printing the bytes here
		System.out.println("Saving attachment: " + Arrays.toString(attachment.getBytes()));

		// Specify the directory where you want to save the file String
		String uploadDirectory = "C:/Users/HP/Downloads/Doc";

		 // Create the directory if it doesn't exist 
		File directory = new File(uploadDirectory); 
		if (!directory.exists()) { directory.mkdirs(); }
//		  
//		 // Save the attachment to the specified directory String fileName =
//		 attachment.getOriginalFilename(); String filePath = uploadDirectory +
//		 fileName; Path destination = Paths.get(filePath); Files.write(destination,
//		 attachment.getBytes());

		String fileName = UUID.randomUUID().toString() + "_" + attachment.getOriginalFilename();

		Path filePath = Paths.get(uploadDirectory, fileName);
		Files.write(filePath, attachment.getBytes());

		System.out.println("Attachment saved to: " + filePath);

		return attachment.getBytes();
	}

	@Override
	public RestaurantDto updateRestaurant(Integer restaurantId, RestaurantDto restaurantDto)
			throws NotFoundException, IOException, AlreadyPresentException {

		logger.info("Updating restaurant with ID: " + restaurantId);
		Restaurant restaurant = restaurantRepository.findById(restaurantId)
				.orElseThrow(() -> new NotFoundException(String.format("Restaurant not found with ID ", restaurantId)));

//		Optional<Restaurant> restName = Optional
//				.ofNullable(restaurantRepository.findByRestaurantName(restaurantDto.getRestaurantName()));
//		if (restName.isPresent()) {
//			throw new AlreadyPresentException("Restaurant already exists");
//		}

		Restaurant existingRestaurant = restaurantRepository.findByRestaurantName(restaurantDto.getRestaurantName());
	    if (existingRestaurant != null && !existingRestaurant.getRestaurantName().equals(restaurant.getRestaurantName())) {
	        throw new AlreadyPresentException("Restaurant with the updated name already exists");
	    }
		BeanUtils.copyProperties(restaurantDto, restaurant);
		if (restaurantDto.getReturnedFoodAttachment() != null) {
			restaurant.setFoodAttachment(restaurantDto.getFoodAttachment().getBytes());
		}
		if (restaurantDto.getReturnedLicenseAttachment() != null) {
			restaurant.setLicenseAttachment(restaurantDto.getLicenseAttachment().getBytes());
		}

		Restaurant updatedRestaurant = restaurantRepository.save(restaurant);
		RestaurantDto updatedRestaurantDto = updatedRestaurant.getRestaurantDto();
//		updatedRestaurantDto.setRestaurantName(updatedRestaurant.getRestaurantName());
//		updatedRestaurantDto.se
		return updatedRestaurantDto;

	}

	/**
	 * Retrieves a set of dishes belonging to a specific Restaurant by its ID.
	 *
	 * @param restaurantId The ID of the category.
	 * @return A set of dishes belonging to the specified restaurant.
	 * @throws RuntimeException if the restaurant with the given ID is not found.
	 */
	@Override
	public List<DishDto> getDishesByRestaurant(Integer restaurantId) throws RuntimeException {
		logger.info("Getting dishes for restaurant with ID: " + restaurantId);
		return dishRepository.findAllByRestaurantRestaurantId(restaurantId).stream().map(Dish::getDishDto)
				.collect(Collectors.toList());
	}

	@Override
	public DishDto addDishByRestaurant(Integer restaurantId, DishDto dishDto)
			throws NotFoundException, AlreadyPresentException, IOException {
		Restaurant restaurant = restaurantRepository.findById(restaurantId)
				.orElseThrow(() -> new NotFoundException("Restaurant not found"));
////		Optional<String> opt = Optional.ofNullable(dishRepository.findAllByRestaurantRestaurantId(restaurantId).stream().findFirst().get().getDishName());
//		if (opt.isPresent()) {
//			throw new AlreadyPresentException("Dish already exists");
//		} else {
		 List<Dish> existingDishes = dishRepository.findAllByRestaurantRestaurantId(restaurantId);
		    for (Dish existingDish : existingDishes) {
		        if (existingDish.getDishName().equals(dishDto.getDishName())) {
		            throw new AlreadyPresentException("Dish with the same name already exists");
		        }
		    }
			Dish dish = new Dish();
			BeanUtils.copyProperties(dishDto, dish);
			dish.setImg(dishDto.getImg().getBytes());
			dish.setRestaurant(restaurant);
			Dish createdDish = dishRepository.save(dish);
			DishDto createdDishDto = new DishDto();
			createdDishDto.setDishId(createdDish.getDishId());
			createdDishDto.setReturnedImg(createdDish.getImg());
			return createdDishDto;
//		}
	}

	@Override
	public void removeRestaurant(Integer restaurantId) throws NotFoundException {
		logger.info("Deleting restaurant with ID: " + restaurantId);
		Restaurant restaurant = restaurantRepository.findById(restaurantId)
				.orElseThrow(() -> new NotFoundException(String.format("Restaurant not found with ID ", restaurantId)));
		restaurantRepository.deleteById(restaurant.getRestaurantId());
		logger.info("Restaurant deleted with ID: {}", restaurantId);
	}

	@Override
	public List<Restaurant> viewAllRestaurant() throws EmptyFieldException {
		logger.info("Getting all restaurants");
		List<Restaurant> listOfRestaurants = restaurantRepository.findAll();
		if (listOfRestaurants.isEmpty()) {
			throw new EmptyFieldException("602", "The list is empty");
		} else {
			logger.info("Total categories retrieved: {}", listOfRestaurants.size());
			return listOfRestaurants;
		}
	}

	@Override
	public List<Restaurant> getAllRestaurantsByTitle(String title) {
		// TODO Auto-generated method stub
		return restaurantRepository.findAllByRestaurantNameContaining(title);
	}

	@Override
	public List<DishDto> getDishesByRestaurantAndTitle(Integer restaurantId, String title) {
		// TODO Auto-generated method stub
		return dishRepository.findAllByRestaurantRestaurantIdAndDishNameContaining(restaurantId, title).stream()
				.map(Dish::getDishDto).collect(Collectors.toList());
	}

	@Override
	public RestaurantDto getRestaurantById(Integer restaurantId) {
		logger.info("Searching for restaurant with id: " + restaurantId);
		Optional<Restaurant> restaurant = restaurantRepository.findById(restaurantId);
		if (restaurant.isPresent()) {
			RestaurantDto restaurantDto = restaurant.get().getRestaurantDto();
			return restaurantDto;
		} else {
			throw new NullFieldException("601", "name of the dish is null");
		}
	}

}
