package com.example.alonedeliveryproject.web.food.service;

import com.example.alonedeliveryproject.domain.food.Food;
import com.example.alonedeliveryproject.domain.food.repository.FoodRepository;
import com.example.alonedeliveryproject.domain.restaurant.Repository.RestaurantRepository;
import com.example.alonedeliveryproject.domain.restaurant.Restaurant;
import com.example.alonedeliveryproject.web.food.dto.FoodDto.Request;
import com.example.alonedeliveryproject.web.food.exception.FoodException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FoodService {

  private final FoodRepository foodRepository;
  private final RestaurantRepository restaurantRepository;

  public void save(List<Request> foodsRequest, long restaurantId) {
    Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(
        () -> new FoodException("음식점을 찾을 수 없습니다.")
    );

    for (Request foodRequest : foodsRequest) {
      Food food = foodRequest.toEntity();
      food.addRestaurant(restaurant);
      foodRepository.save(food);
    }
  }
}
