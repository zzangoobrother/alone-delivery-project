package com.example.alonedeliveryproject.web.restaurant.service;

import com.example.alonedeliveryproject.domain.restaurant.Repository.RestaurantRepository;
import com.example.alonedeliveryproject.domain.restaurant.Restaurant;
import com.example.alonedeliveryproject.web.restaurant.dto.RestaurantSaveDto;
import com.example.alonedeliveryproject.web.restaurant.exception.RestaurantException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RestaurantService {

  private final RestaurantRepository restaurantRepository;

  public Restaurant restaurantSave(RestaurantSaveDto.Request request) {
    if (request.checkMinOrderPriceHundredUnit()) {
      throw new RestaurantException("주문금액은 100원 단위로 입력가능 합니다.");
    }

    if (request.checkDeliveryFeeFiveHundredUnit()) {
      throw new RestaurantException("배달비는 500원 단위로 입력가능 합니다.");
    }

    Restaurant restaurant = request.toEntity();
    return restaurantRepository.save(restaurant);
  }
}
