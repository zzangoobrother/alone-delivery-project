package com.example.alonedeliveryproject.web.order.dto;

import com.example.alonedeliveryproject.domain.food.Food;
import com.example.alonedeliveryproject.domain.order.Order;
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

  public Order toEntity(Food food) {
    return Order.builder()
        .quantity(this.quantity)
        .food(food)
        .build();
  }
}
