package com.example.alonedeliveryproject.web.food.service;

import com.example.alonedeliveryproject.domain.food.Food;
import com.example.alonedeliveryproject.domain.food.repository.FoodRepository;
import com.example.alonedeliveryproject.domain.restaurant.Repository.RestaurantRepository;
import com.example.alonedeliveryproject.domain.restaurant.Restaurant;
import com.example.alonedeliveryproject.web.food.dto.FoodDto.Request;
import com.example.alonedeliveryproject.web.food.exception.FoodException;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FoodService {

  private final FoodRepository foodRepository;
  private final RestaurantRepository restaurantRepository;

  @Transactional
  public void save(List<Request> foodsRequest, long restaurantId) {
    Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(
        () -> new FoodException("음식점을 찾을 수 없습니다.")
    );

    for (Request foodRequest : foodsRequest) {
      if (foodRequest.checkFoodPriceHundredUnit()) {
        throw new FoodException("음식 가격은 100원 단위로 입력가능 합니다.");
      }

      Food food = foodRequest.toEntity();
      food.addRestaurant(restaurant);

      try {
        foodRepository.save(food);
      } catch (DataIntegrityViolationException e) {
        throw new FoodException("같은 음식점 내에서 중복된 음식을 등록할 수 없습니다.");
      }
    }
  }
}
