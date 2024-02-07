package com.foodapp.dtos;



import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class DishDto {

	
	private Integer dishId;
	private String dishName;
	private Double cost;
	private byte[] returnedImg;
	private MultipartFile img;
	
	private Integer restaurantId;
	
	private String restaurantName;
	
	
}
