package com.foodapp.dtos;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class RestaurantDto {
	
	private String restaurantName;
	
	private String description;

    private MultipartFile foodAttachment;

    private MultipartFile licenseAttachment;
    
    private byte[] returnedFoodAttachment;
    
    private byte[] returnedLicenseAttachment;

}
