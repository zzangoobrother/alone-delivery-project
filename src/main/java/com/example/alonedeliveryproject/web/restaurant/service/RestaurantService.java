package com.example.alonedeliveryproject.web.restaurant.service;

import static java.util.stream.Collectors.toList;

import com.example.alonedeliveryproject.domain.restaurant.Repository.RestaurantRepository;
import com.example.alonedeliveryproject.domain.restaurant.Restaurant;
import com.example.alonedeliveryproject.web.restaurant.dto.RestaurantDto;
import com.example.alonedeliveryproject.web.restaurant.dto.RestaurantDto.Response;
import com.example.alonedeliveryproject.web.restaurant.exception.RestaurantException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RestaurantService {

  private final RestaurantRepository restaurantRepository;

  public Restaurant restaurantSave(RestaurantDto.Request request) {
    if (request.checkMinOrderPriceHundredUnit()) {
      throw new RestaurantException("주문금액은 100원 단위로 입력가능 합니다.");
    }

    if (request.checkDeliveryFeeFiveHundredUnit()) {
      throw new RestaurantException("배달비는 500원 단위로 입력가능 합니다.");
    }

    Restaurant restaurant = request.toEntity();
    return restaurantRepository.save(restaurant);
  }

  public List<Response> getRestaurants() {
    return restaurantRepository.findAll()
        .stream()
        .map(restaurant ->
            Response.builder()
            .name(restaurant.getName())
            .minOrderPrice(restaurant.getMinOrderPrice())
            .deliveryFee(restaurant.getDeliveryFee())
            .build()
        ).collect(toList());
  }
}
