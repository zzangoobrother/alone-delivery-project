package com.example.alonedeliveryproject.web.restaurant.dto;

import com.example.alonedeliveryproject.domain.restaurant.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class RestaurantSaveDto {

  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  @Builder
  public static class Request {
    private String name;
    private int minOrderPrice;
    private int deliveryFee;

    public Restaurant toEntity() {
      return Restaurant.builder()
          .name(this.name)
          .minOrderPrice(this.minOrderPrice)
          .deliveryFee(this.deliveryFee)
          .build();
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }

      Request request = (Request) o;

      if (minOrderPrice != request.minOrderPrice) {
        return false;
      }
      if (deliveryFee != request.deliveryFee) {
        return false;
      }
      return name != null ? name.equals(request.name) : request.name == null;
    }

    @Override
    public int hashCode() {
      int result = name != null ? name.hashCode() : 0;
      result = 31 * result + minOrderPrice;
      result = 31 * result + deliveryFee;
      return result;
    }
  }
}
