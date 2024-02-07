package com.foodapp.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.foodapp.dtos.DishDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "dish")
public class Dish {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer dishId;
	private String dishName;
	private Double cost;
	
	@Lob
	@Column(columnDefinition = "longblob")
	private byte[] img;
	
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="restaurantId", nullable=false)
	@JsonIgnore
	private Restaurant restaurant;
	
	public DishDto getDishDto() {
		DishDto dishDto = new DishDto();
		dishDto.setDishId(dishId);
		dishDto.setDishName(dishName);
//		dishDto.setQuantity(quantity);
		dishDto.setCost(cost);
		dishDto.setReturnedImg(img);
		dishDto.setRestaurantId(restaurant.getRestaurantId());
		dishDto.setRestaurantName(restaurant.getRestaurantName());
		return dishDto;
	}
}
