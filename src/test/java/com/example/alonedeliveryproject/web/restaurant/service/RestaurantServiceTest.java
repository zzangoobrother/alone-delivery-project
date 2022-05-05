package com.example.alonedeliveryproject.web.restaurant.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.alonedeliveryproject.domain.restaurant.Repository.RestaurantRepository;
import com.example.alonedeliveryproject.domain.restaurant.Restaurant;
import com.example.alonedeliveryproject.web.restaurant.dto.RestaurantSaveDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RestaurantServiceTest {

  @Mock
  private RestaurantRepository restaurantRepository;

  @Autowired
  private RestaurantService restaurantService;

  private RestaurantSaveDto.Request restaurantSaveRequestDto;

  @BeforeEach
  void setup() {
    restaurantSaveRequestDto = RestaurantSaveDto.Request.builder()
        .name("쉑쉑 강남점")
        .minOrderPrice(100_000)
        .deliveryFee(10_000)
        .build();
  }

  @Test
  void 음식점_등록() {
    Restaurant saveRestaurant = restaurantService.restaurantSave(restaurantSaveRequestDto);

    RestaurantSaveDto.Request saveRestaurantSaveRequestDto
        = RestaurantSaveDto.Request.builder()
        .name("쉑쉑 강남점")
        .minOrderPrice(100_000)
        .deliveryFee(10_000)
        .build();

    assertThat(restaurantSaveRequestDto).isEqualTo(saveRestaurantSaveRequestDto);
  }
}