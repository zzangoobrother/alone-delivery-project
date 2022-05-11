package com.example.alonedeliveryproject.web.food.dto;

import com.example.alonedeliveryproject.domain.food.Food;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class FoodDto {

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class Request {
    private String name;

    private int price;

    public Food toEntity() {
      return Food.builder()
          .name(this.name)
          .price(this.price)
          .build();
    }

    public boolean checkFoodPriceHundredUnit() {
      if (this.price % 100 == 0) {
        return false;
      }
      return true;
    }
  }
}
