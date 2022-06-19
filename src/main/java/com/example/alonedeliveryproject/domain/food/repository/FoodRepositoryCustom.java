package com.example.alonedeliveryproject.domain.food.repository;

import com.example.alonedeliveryproject.domain.food.Food;
import com.example.alonedeliveryproject.domain.restaurant.Restaurant;
import java.util.List;

public interface FoodRepositoryCustom {

  List<Food> findByRestaurantAndNameIn(Restaurant restaurant, List<String> names);

  List<Food> findByRestaurant(Restaurant restaurant);
}
