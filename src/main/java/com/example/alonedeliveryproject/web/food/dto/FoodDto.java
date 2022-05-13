package com.example.alonedeliveryproject.web.food.dto;

import com.example.alonedeliveryproject.domain.food.Food;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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

    @Min(value = 100, message = "최소 음식 가격은 100원 입니다.")
    @Max(value = 1000000, message = "최대 음식 가격은 1,000,000원 입니다.")
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

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Response {

    private long id;

    private String name;

    private int price;
  }
}
