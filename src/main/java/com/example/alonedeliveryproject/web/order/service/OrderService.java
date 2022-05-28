package com.example.alonedeliveryproject.web.order.service;

import com.example.alonedeliveryproject.domain.food.Food;
import com.example.alonedeliveryproject.domain.food.repository.FoodRepository;
import com.example.alonedeliveryproject.domain.order.Order;
import com.example.alonedeliveryproject.domain.order.OrderFood;
import com.example.alonedeliveryproject.domain.order.repository.OrderFoodRepository;
import com.example.alonedeliveryproject.domain.order.repository.OrderRepository;
import com.example.alonedeliveryproject.domain.restaurant.Restaurant;
import com.example.alonedeliveryproject.web.order.dto.OrderDtoRequest;
import com.example.alonedeliveryproject.web.order.dto.OrderDtoResponse;
import com.example.alonedeliveryproject.web.order.dto.OrderDtoResponse.FoodResponse;
import com.example.alonedeliveryproject.web.order.dto.OrderFoods;
import com.example.alonedeliveryproject.web.order.exception.OrderException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class OrderService {

  private final FoodRepository foodRepository;
  private final OrderRepository orderRepository;
  private final OrderFoodRepository orderFoodRepository;

  @Transactional
  public OrderDtoResponse save(long restaurantId, OrderFoods orderFoods) {
    List<OrderDtoRequest> orderDtoRequests = orderFoods.getOrderFoods();

    Map<Long, OrderDtoRequest> orderDtoRequestMap = new HashMap<>();
    orderDtoRequests.forEach(orderDtoRequest -> orderDtoRequestMap.put(orderDtoRequest.getId(), orderDtoRequest));

    List<Food> findFoods = foodRepository.findAllById(orderFoods.getFoodIds());

    Order order = orderDtoRequests.get(0).toEntityOrder();
    orderRepository.save(order);

    List<FoodResponse> foods = new ArrayList<>();
    int totalPrice = 0;
    for (Food findFood : findFoods) {
      if (!findFood.getRestaurant().getId().equals(restaurantId)) {
        throw new OrderException("해당 음식점에서 음식을 찾을 수 없습니다.");
      }
      OrderDtoRequest orderDtoRequest = orderDtoRequestMap.get(findFood.getId());
      OrderFood orderFood = orderDtoRequest.toEntityOrderFood(order, findFood);
      orderFoodRepository.save(orderFood);

      foods.add(makeFoodResponse(findFood.getName(), orderFood.getQuantity(), findFood.getPrice()));
      totalPrice += orderFood.caculationPrice(findFood.getPrice());
    }

    Restaurant restaurant = findFoods.get(0).getRestaurant();
    totalPrice += restaurant.getDeliveryFee();

    if (restaurant.checkMinPrice(totalPrice)) {
      throw new OrderException("해당 음식점의 최소 주문 금액은 " + restaurant.getMinOrderPrice() + "원 입니다.");
    }

    return makeOrderDtoResponse(order.getId(), restaurant.getName(), foods, restaurant.getDeliveryFee(), totalPrice);
  }

  public OrderDtoResponse getOrders(Long orderId) {
    Order order = orderRepository.findById(orderId).orElseThrow(
        () -> new OrderException("해당 주문번호를 찾을 수 없습니다.")
    );

    List<OrderFood> orderFoods = orderFoodRepository.findAllByOrder(order);
    List<FoodResponse> foods = new ArrayList<>();
    int totalPrice = 0;
    for (OrderFood orderFood : orderFoods) {
      Food food = orderFood.getFood();

      foods.add(makeFoodResponse(food.getName(), orderFood.getQuantity(), food.getPrice()));
      totalPrice += orderFood.caculationPrice(food.getPrice());
    }

    Restaurant restaurant = orderFoods.get(0).getFood().getRestaurant();
    totalPrice += restaurant.getDeliveryFee();

    return makeOrderDtoResponse(orderId, restaurant.getName(), foods, restaurant.getDeliveryFee(), totalPrice);
  }

  private FoodResponse makeFoodResponse(String foodName, int quantity, int price) {
    return FoodResponse.builder()
        .name(foodName)
        .quantity(quantity)
        .price(price)
        .build();
  }

  private OrderDtoResponse makeOrderDtoResponse(Long orderId, String restaurantName, List<FoodResponse> foods, int deliveryFee, int totalPrice) {
    return OrderDtoResponse.builder()
        .orderId(orderId)
        .restaurantName(restaurantName)
        .foods(foods)
        .deliveryFee(deliveryFee)
        .totalPrice(totalPrice)
        .build();
    
  }
}