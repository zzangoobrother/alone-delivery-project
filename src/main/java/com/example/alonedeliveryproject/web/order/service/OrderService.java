package com.example.alonedeliveryproject.web.order.service;

import static java.util.stream.Collectors.toList;

import com.example.alonedeliveryproject.domain.food.Food;
import com.example.alonedeliveryproject.domain.food.repository.FoodRepository;
import com.example.alonedeliveryproject.domain.order.Order;
import com.example.alonedeliveryproject.domain.order.repository.OrderRepository;
import com.example.alonedeliveryproject.web.order.dto.OrderDtoRequest;
import com.example.alonedeliveryproject.web.order.dto.OrderDtoResponse;
import com.example.alonedeliveryproject.web.order.dto.OrderDtoResponse.FoodResponse;
import com.example.alonedeliveryproject.web.order.exception.OrderException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class OrderService {

  private final FoodRepository foodRepository;
  private final OrderRepository orderRepository;

  @Transactional
  public OrderDtoResponse save(long restaurantId, List<OrderDtoRequest> orderFoods) {
    List<FoodResponse> foods = new ArrayList<>();
    int totalPrice = 0;

    List<Long> foodIds = orderFoods.stream().map(OrderDtoRequest::getId).collect(toList());
    List<Food> findFoods = foodRepository.findAllById(foodIds);

    for (Food findFood : findFoods) {
      if (!findFood.getRestaurant().getId().equals(restaurantId)) {
        throw new OrderException("해당 음식점에서 음식을 찾을 수 없습니다.");
      }
      OrderDtoRequest orderFood = null;
      for (int i = 0; i < orderFoods.size(); i++) {
        orderFood = orderFoods.get(i);
        if (orderFood.getId() == findFood.getId()) {
          break;
        }
      }

      Order order = orderFood.toEntity(findFood);
      orderRepository.save(order);

      foods.add(FoodResponse.builder()
                .name(findFood.getName())
                .quantity(orderFood.getQuantity())
                .price(findFood.getPrice())
                .build());

      totalPrice += findFood.getPrice() * orderFood.getQuantity();
    }

    return OrderDtoResponse.builder()
        .restaurantName(findFoods.get(0).getRestaurant().getName())
        .foods(foods)
        .deliveryFee(findFoods.get(0).getRestaurant().getDeliveryFee())
        .totalPrice(totalPrice)
        .build();
  }
}
