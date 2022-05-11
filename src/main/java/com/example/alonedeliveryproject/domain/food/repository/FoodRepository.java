package com.example.alonedeliveryproject.domain.food.repository;

import com.example.alonedeliveryproject.domain.food.Food;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<Food, Long> {

}
