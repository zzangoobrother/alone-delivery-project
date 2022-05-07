package com.example.alonedeliveryproject.web.restaurant.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.example.alonedeliveryproject.domain.restaurant.Repository.RestaurantRepository;
import com.example.alonedeliveryproject.domain.restaurant.Restaurant;
import com.example.alonedeliveryproject.web.restaurant.dto.RestaurantSaveDto;
import com.example.alonedeliveryproject.web.restaurant.dto.RestaurantSaveDto.Request;
import com.example.alonedeliveryproject.web.restaurant.exception.RestaurantException;
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

  @Test
  void 주문금액_100원_단위_입력_에러() {
    RestaurantException restaurantException = assertThrows(RestaurantException.class, () -> {
      restaurantService.restaurantSave(Request.builder()
          .name("")
          .minOrderPrice(2220)
          .deliveryFee(1000)
          .build());
    });

    assertEquals("주문금액은 100원 단위로 입력가능 합니다.", restaurantException.getMessage());
  }

  @Test
  void 배달비_500원_단위_입력_에러() {
    RestaurantException restaurantException = assertThrows(RestaurantException.class, () -> {
      restaurantService.restaurantSave(Request.builder()
          .name("")
          .minOrderPrice(10000)
          .deliveryFee(2200)
          .build());
    });

    assertEquals("배달비는 500원 단위로 입력가능 합니다.", restaurantException.getMessage());
  }
}