package com.example.alonedeliveryproject.web.restaurant.dto;

import com.example.alonedeliveryproject.domain.restaurant.Restaurant;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class RestaurantDto {

  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  @Builder
  public static class Request {
    private String name;

    @Min(value = 1000, message = "최소 주문 금액은 1,000원 입니다.")
    @Max(value = 100000, message = "최대 주문 금액은 100,000원 입니다.")
    private int minOrderPrice;

    @Min(value = 0, message = "최소 배달비 금액은 0원 입니다.")
    @Max(value = 10000, message = "최대 배달비 금액은 10,000원 입니다.")
    private int deliveryFee;

    public boolean checkMinOrderPriceHundredUnit() {
      return !(minOrderPrice % 100 == 0);
    }

    public boolean checkDeliveryFeeFiveHundredUnit() {
      return !(deliveryFee % 500 == 0);
    }

    public Restaurant toEntity() {
      return Restaurant.builder()
          .name(this.name)
          .minOrderPrice(this.minOrderPrice)
          .deliveryFee(this.deliveryFee)
          .build();
    }
  }

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class Response {
    private String name;

    private int minOrderPrice;

    private int deliveryFee;
  }
}
