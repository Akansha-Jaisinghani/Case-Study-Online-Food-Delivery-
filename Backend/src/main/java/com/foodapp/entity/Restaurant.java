package com.foodapp.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.foodapp.dtos.DishDto;
import com.foodapp.dtos.RestaurantDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "restaurant")
public class Restaurant {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer restaurantId;
	private String restaurantName;
	private String description;
	
	@Lob
	private byte[] foodAttachment;  // Attachment for food

	@Lob
	private byte[] licenseAttachment;  // Attachment for license

//	@OneToMany(mappedBy = "restaurant")
	@OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Dish> dishes = new ArrayList<>();

	public RestaurantDto getRestaurantDto() {
		RestaurantDto restaurantDto = new RestaurantDto();
		restaurantDto.setRestaurantName(restaurantName);
		restaurantDto.setDescription(description);
		restaurantDto.setReturnedFoodAttachment(foodAttachment);
		restaurantDto.setReturnedLicenseAttachment(licenseAttachment);
		return restaurantDto;
	}

}
