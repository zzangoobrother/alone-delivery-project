package com.example.alonedeliveryproject.web.order.service;

import static java.util.Optional.ofNullable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.example.alonedeliveryproject.domain.food.Food;
import com.example.alonedeliveryproject.domain.food.repository.FoodRepository;
import com.example.alonedeliveryproject.domain.order.Order;
import com.example.alonedeliveryproject.domain.order.OrderFood;
import com.example.alonedeliveryproject.domain.order.repository.OrderFoodRepository;
import com.example.alonedeliveryproject.domain.order.repository.OrderRepository;
import com.example.alonedeliveryproject.domain.restaurant.Restaurant;
import com.example.alonedeliveryproject.web.order.dto.OrderDtoRequest;
import com.example.alonedeliveryproject.web.order.dto.OrderDtoResponse;
import com.example.alonedeliveryproject.web.order.dto.OrderFoods;
import com.example.alonedeliveryproject.web.order.exception.OrderException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

  @Mock
  private OrderRepository orderRepository;

  @Mock
  private OrderFoodRepository orderFoodRepository;

  @Mock
  private FoodRepository foodRepository;

  @InjectMocks
  private OrderService orderService;

  private Restaurant restaurant;

  private Food food;

  private Food food2;

  private Food food3;

  private List<OrderDtoRequest> orderDtos = new ArrayList<>();

  private List<Food> foods = new ArrayList<>();

  @BeforeEach
  void setup() {
    restaurant = new Restaurant(1L, "쉑쉑 강남점", 20_000, 10_000);

    food = new Food(1L, "쉑버거 더블", 10900, restaurant);
    food2 = new Food(2L, "쉑 치킨 버거", 9900, restaurant);
    food3 = new Food(3L, "쉑 비프 버거", 11900, restaurant);

    foods.add(food);
    foods.add(food2);
    foods.add(food3);

    orderDtos.add(new OrderDtoRequest(food.getId(), 1));
    orderDtos.add(new OrderDtoRequest(food2.getId(), 2));
    orderDtos.add(new OrderDtoRequest(food3.getId(), 3));
  }

  @Test
  void 주문하기_정상() {
    given(foodRepository.findAllById(any())).willReturn(foods);

    OrderDtoResponse result = orderService.save(1L, new OrderFoods(orderDtos));
    assertThat(restaurant.getName()).isEqualTo(result.getRestaurantName());

    assertThat(food.getName()).isEqualTo(result.getFoods().get(0).getName());
    assertThat(food.getPrice()).isEqualTo(result.getFoods().get(0).getPrice());

    assertThat(food2.getName()).isEqualTo(result.getFoods().get(1).getName());
    assertThat(food2.getPrice()).isEqualTo(result.getFoods().get(1).getPrice());

    assertThat(food3.getName()).isEqualTo(result.getFoods().get(2).getName());
    assertThat(food3.getPrice()).isEqualTo(result.getFoods().get(2).getPrice());

    assertThat(result.getDeliveryFee()).isEqualTo(restaurant.getDeliveryFee());

    int totalPrice = result.getFoods().get(0).getPrice() * orderDtos.get(0).getQuantity()
        + result.getFoods().get(1).getPrice() * orderDtos.get(1).getQuantity()
        + result.getFoods().get(2).getPrice() * orderDtos.get(2).getQuantity()
        + restaurant.getDeliveryFee();
    assertThat(result.getTotalPrice()).isEqualTo(totalPrice);
  }

  @Test
  void 해당_음식점_최소주문금액_보다_적음_오류() {
    restaurant = new Restaurant(1L, "쉑쉑 강남점", 100_000, 10_000);

    foods = new ArrayList<>();
    foods.add(new Food(1L, "쉑버거 더블", 10900, restaurant));
    foods.add(new Food(2L, "쉑 치킨 버거", 9900, restaurant));
    foods.add(new Food(3L, "쉑 비프 버거", 11900, restaurant));

    given(foodRepository.findAllById(any())).willReturn(foods);

    OrderException orderException = assertThrows(OrderException.class, () -> {
      orderService.save(1L, new OrderFoods(orderDtos));
    });

    assertEquals("해당 음식점의 최소 주문 금액은 " + restaurant.getMinOrderPrice() + "원 입니다.", orderException.getMessage());
  }

  @Test
  void 음식점_주문조회() {
    Order order = new Order(1L);
    List<OrderFood> orderFoods = new ArrayList<>();
    orderFoods.add(OrderFood.builder()
                    .quantity(1)
                    .order(order)
                    .food(food)
                    .build());

    orderFoods.add(OrderFood.builder()
        .quantity(2)
        .order(order)
        .food(food2)
        .build());

    orderFoods.add(OrderFood.builder()
        .quantity(3)
        .order(order)
        .food(food3)
        .build());

    given(orderRepository.findById(any())).willReturn(ofNullable(order));
    given(orderFoodRepository.findAllByOrder(any())).willReturn(orderFoods);

    OrderDtoResponse result = orderService.getOrders(order.getId());

    assertThat(food.getName()).isEqualTo(result.getFoods().get(0).getName());
    assertThat(food.getPrice()).isEqualTo(result.getFoods().get(0).getPrice());

    assertThat(food2.getName()).isEqualTo(result.getFoods().get(1).getName());
    assertThat(food2.getPrice()).isEqualTo(result.getFoods().get(1).getPrice());

    assertThat(food3.getName()).isEqualTo(result.getFoods().get(2).getName());
    assertThat(food3.getPrice()).isEqualTo(result.getFoods().get(2).getPrice());

    assertThat(result.getDeliveryFee()).isEqualTo(restaurant.getDeliveryFee());

    int totalPrice = result.getFoods().get(0).getPrice() * orderDtos.get(0).getQuantity()
        + result.getFoods().get(1).getPrice() * orderDtos.get(1).getQuantity()
        + result.getFoods().get(2).getPrice() * orderDtos.get(2).getQuantity()
        + restaurant.getDeliveryFee();
    assertThat(result.getTotalPrice()).isEqualTo(totalPrice);
  }
}