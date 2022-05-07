package com.example.alonedeliveryproject.web.restaurant.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.example.alonedeliveryproject.domain.restaurant.Repository.RestaurantRepository;
import com.example.alonedeliveryproject.domain.restaurant.Restaurant;
import com.example.alonedeliveryproject.web.restaurant.dto.RestaurantSaveDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RestaurantServiceTest {

  @Mock
  private RestaurantRepository restaurantRepository;

  @InjectMocks
  private RestaurantService restaurantService;

  private Restaurant restaurant;

  private RestaurantSaveDto.Request restaurantSaveRequestDto;

  @BeforeEach
  void setup() {
    restaurant = Restaurant.builder()
        .name("쉑쉑 강남점")
        .minOrderPrice(100_000)
        .deliveryFee(10_000)
        .build();

    restaurantSaveRequestDto = RestaurantSaveDto.Request.builder()
        .name("쉑쉑 강남점")
        .minOrderPrice(100_000)
        .deliveryFee(10_000)
        .build();
  }

  @Test
  void 음식점_등록() {
    ArgumentCaptor<Restaurant> captor = ArgumentCaptor.forClass(Restaurant.class);
    given(restaurantRepository.save(any())).willReturn(restaurant);

    restaurantService.restaurantSave(restaurantSaveRequestDto);

    verify(restaurantRepository, times(1)).save(captor.capture());

    Restaurant saveRestaurant = captor.getValue();

    assertThat(restaurantSaveRequestDto.getName()).isEqualTo(saveRestaurant.getName());
    assertThat(restaurantSaveRequestDto.getMinOrderPrice()).isEqualTo(saveRestaurant.getMinOrderPrice());
  }
}