package com.example.alonedeliveryproject.web.restaurant.service;

import com.example.alonedeliveryproject.domain.restaurant.Repository.RestaurantRepository;
import com.example.alonedeliveryproject.domain.restaurant.Restaurant;
import com.example.alonedeliveryproject.web.restaurant.dto.RestaurantSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RestaurantService {

  private final RestaurantRepository restaurantRepository;

  public Restaurant restaurantSave(RestaurantSaveDto.Request request) {
    Restaurant restaurant = request.toEntity();
    return restaurantRepository.save(restaurant);
  }
}
