package com.example.alonedeliveryproject.web.order.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.alonedeliveryproject.domain.food.Food;
import com.example.alonedeliveryproject.domain.restaurant.Restaurant;
import com.example.alonedeliveryproject.web.order.dto.OrderDtoRequest;
import com.example.alonedeliveryproject.web.order.dto.OrderDtoResponse;
import com.example.alonedeliveryproject.web.order.dto.OrderDtoResponse.FoodResponse;
import com.example.alonedeliveryproject.web.order.dto.OrderFoods;
import com.example.alonedeliveryproject.web.order.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(OrderController.class)
@MockBean(JpaMetamodelMappingContext.class)
class OrderControllerTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private OrderService orderService;

  private Restaurant restaurant;

  private Food food;

  private Food food2;

  private Food food3;

  private List<OrderDtoRequest> orderDtos = new ArrayList<>();

  private List<FoodResponse> foods = new ArrayList<>();

  private OrderDtoResponse orderDtoResponse;

  @BeforeEach
  void setup() {
    restaurant = new Restaurant(1L, "쉑쉑 강남점", 20_000, 10_000);

    food = new Food(1L, "쉑버거 더블", 10900, restaurant);
    food2 = new Food(2L, "쉑 치킨 버거", 9900, restaurant);
    food3 = new Food(3L, "쉑 비프 버거", 11900, restaurant);

    orderDtos.add(new OrderDtoRequest(food.getId(), 1));
    orderDtos.add(new OrderDtoRequest(food2.getId(), 2));
    orderDtos.add(new OrderDtoRequest(food3.getId(), 3));

    foods.add(FoodResponse.builder()
        .name(food.getName())
        .quantity(orderDtos.get(0).getQuantity())
        .price(food.getPrice())
        .build());

    foods.add(FoodResponse.builder()
        .name(food2.getName())
        .quantity(orderDtos.get(1).getQuantity())
        .price(food2.getPrice())
        .build());

    foods.add(FoodResponse.builder()
        .name(food3.getName())
        .quantity(orderDtos.get(2).getQuantity())
        .price(food3.getPrice())
        .build());

    orderDtoResponse = OrderDtoResponse.builder()
        .restaurantName(restaurant.getName())
        .foods(foods)
        .deliveryFee(restaurant.getDeliveryFee())
        .totalPrice(66400)
        .build();
  }

  @Test
  void 주문하기_정상() throws Exception {
    given(orderService.save(anyLong(), any())).willReturn(orderDtoResponse);

    mvc.perform(post("/orders/" + 1)
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(new OrderFoods(orderDtos))))
        .andExpect(status().isOk());
  }

  @Test
  void 음식점_주문조회() throws Exception {
    given(orderService.getOrders(anyLong())).willReturn(orderDtoResponse);

    mvc.perform(get("/orders/" + 1))
        .andExpect(status().isOk());
  }
}