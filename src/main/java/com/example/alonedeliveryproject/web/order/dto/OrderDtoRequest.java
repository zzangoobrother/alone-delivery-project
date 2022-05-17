package com.example.alonedeliveryproject.web.order.dto;

import com.example.alonedeliveryproject.domain.food.Food;
import com.example.alonedeliveryproject.domain.order.Order;
import com.example.alonedeliveryproject.domain.order.OrderFood;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDtoRequest {
  private long id;

  private int quantity;

  public Order toEntityOrder() {
    return new Order();
  }

  public OrderFood toEntityOrderFood(Order order, Food food) {
    return OrderFood.builder()
        .quantity(this.quantity)
        .order(order)
        .food(food)
        .build();
  }
}
