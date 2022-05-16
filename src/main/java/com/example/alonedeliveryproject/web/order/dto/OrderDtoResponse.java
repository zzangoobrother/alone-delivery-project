package com.example.alonedeliveryproject.web.order.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDtoResponse {

  private String restaurantName;

  private List<FoodResponse> foods;

  private long deliveryFee;

  private long totalPrice;

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class FoodResponse {
    private String name;

    private int quantity;

    private int price;
  }
}
