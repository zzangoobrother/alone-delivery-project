package com.example.alonedeliveryproject.domain.food.repository;

import com.example.alonedeliveryproject.domain.food.Food;
import com.example.alonedeliveryproject.domain.restaurant.Restaurant;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<Food, Long> {

  List<Food> findByRestaurantAndNameIn(Restaurant restaurant, List<String> names);

  List<Food> findByRestaurant(Restaurant restaurant);
}
