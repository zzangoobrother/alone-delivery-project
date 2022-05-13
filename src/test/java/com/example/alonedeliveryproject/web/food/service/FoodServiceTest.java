package com.example.alonedeliveryproject.web.food.service;

import static java.util.Optional.ofNullable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.example.alonedeliveryproject.domain.food.Food;
import com.example.alonedeliveryproject.domain.food.repository.FoodRepository;
import com.example.alonedeliveryproject.domain.restaurant.Repository.RestaurantRepository;
import com.example.alonedeliveryproject.domain.restaurant.Restaurant;
import com.example.alonedeliveryproject.web.food.dto.FoodDto.Request;
import com.example.alonedeliveryproject.web.food.dto.FoodDto.Response;
import com.example.alonedeliveryproject.web.food.dto.FoodSaveDtos;
import com.example.alonedeliveryproject.web.food.exception.FoodException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FoodServiceTest {

  @Mock
  private FoodRepository foodRepository;

  @Mock
  private RestaurantRepository restaurantRepository;

  @InjectMocks
  private FoodService foodService;

  private Restaurant restaurant;

  private Request foodDtoRequest;

  private Request foodDtoRequest2;

  private Request foodDtoRequest3;

  List<Food> foods = new ArrayList<>();

  @BeforeEach
  void setup() {
    restaurant = Restaurant.builder()
        .name("쉑쉑 강남점")
        .minOrderPrice(100_000)
        .deliveryFee(10_000)
        .build();

    foodDtoRequest = Request.builder()
        .name("쉑버거 더블")
        .price(10900)
        .build();

    foodDtoRequest2 = Request.builder()
        .name("쉑 치킨 버거")
        .price(9900)
        .build();

    foodDtoRequest3 = Request.builder()
        .name("쉑 비프 버거")
        .price(11900)
        .build();

    foods.add(new Food(1L, "쉑버거 더블", 10900, restaurant));
    foods.add(new Food(2L, "쉑 치킨 버거", 9900, restaurant));
    foods.add(new Food(3L, "쉑 비프 버거", 11900, restaurant));
  }

  @Test
  void 음식_1개_등록_정상() {
    ArgumentCaptor<Food> captor = ArgumentCaptor.forClass(Food.class);
    given(restaurantRepository.findById(any())).willReturn(ofNullable(restaurant));

    List<Request> foodsRequest = new ArrayList<>();
    foodsRequest.add(foodDtoRequest);

    foodService.save(1L, new FoodSaveDtos(foodsRequest));

    verify(foodRepository, times(1)).save(captor.capture());

    Food saveFood = captor.getValue();

    assertThat(foodDtoRequest.getName()).isEqualTo(saveFood.getName());
    assertThat(foodDtoRequest.getPrice()).isEqualTo(saveFood.getPrice());
  }

  @Test
  void 음식_2개_등록_정상() {
    ArgumentCaptor<Food> captor = ArgumentCaptor.forClass(Food.class);
    given(restaurantRepository.findById(any())).willReturn(ofNullable(restaurant));

    List<Request> foodsRequest = new ArrayList<>();
    foodsRequest.add(foodDtoRequest);
    foodsRequest.add(foodDtoRequest2);

    foodService.save(1L, new FoodSaveDtos(foodsRequest));

    verify(foodRepository, times(2)).save(captor.capture());

    List<Food> saveFoods = captor.getAllValues();

    for (int i = 0; i < saveFoods.size(); i++) {
      assertThat(foodsRequest.get(i).getName()).isEqualTo(saveFoods.get(i).getName());
      assertThat(foodsRequest.get(i).getPrice()).isEqualTo(saveFoods.get(i).getPrice());
    }
  }

  @Test
  void 음식_3개_등록_정상() {
    ArgumentCaptor<Food> captor = ArgumentCaptor.forClass(Food.class);
    given(restaurantRepository.findById(any())).willReturn(ofNullable(restaurant));

    List<Request> foodsRequest = new ArrayList<>();
    foodsRequest.add(foodDtoRequest);
    foodsRequest.add(foodDtoRequest2);
    foodsRequest.add(foodDtoRequest3);

    foodService.save(1L, new FoodSaveDtos(foodsRequest));

    verify(foodRepository, times(3)).save(captor.capture());

    List<Food> saveFoods = captor.getAllValues();

    for (int i = 0; i < saveFoods.size(); i++) {
      assertThat(foodsRequest.get(i).getName()).isEqualTo(saveFoods.get(i).getName());
      assertThat(foodsRequest.get(i).getPrice()).isEqualTo(saveFoods.get(i).getPrice());
    }
  }

  @Test
  void 음식가격_100원_단위_입력_에러() {
    given(restaurantRepository.findById(any())).willReturn(ofNullable(restaurant));

    List<Request> foodsRequest = new ArrayList<>();
    foodsRequest.add(Request.builder().name("쉑 버거").price(770).build());

    FoodException foodException = assertThrows(FoodException.class, () -> {
      foodService.save(1L, new FoodSaveDtos(foodsRequest));
    });

    assertEquals("음식 가격은 100원 단위로 입력가능 합니다.", foodException.getMessage());
  }

  @Test
  void 음식점에_등록된_모든_음식_조회_정상() {
    given(restaurantRepository.findById(any())).willReturn(ofNullable(restaurant));
    given(foodRepository.findByRestaurant(restaurant)).willReturn(foods);

    List<Response> foodResult = foodService.getFoods(1L);

    for (int i = 0; i < foodResult.size(); i++) {
      assertThat(foodResult.get(i).getName()).isEqualTo(foods.get(i).getName());
      assertThat(foodResult.get(i).getPrice()).isEqualTo(foods.get(i).getPrice());
    }
  }
}