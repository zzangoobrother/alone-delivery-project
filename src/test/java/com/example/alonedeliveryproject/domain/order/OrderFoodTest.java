package com.example.alonedeliveryproject.domain.order;

import static org.junit.Assert.*;

import com.example.alonedeliveryproject.domain.food.Food;
import com.example.alonedeliveryproject.domain.restaurant.Restaurant;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderFoodTest {

  private OrderFood orderFood;

  private Food food;

  @Before
  public void setup() {
    Restaurant restaurant = new Restaurant(1L, "쉑쉑 강남점", 20_000, 10_000);
    food = new Food(1L, "쉑버거 더블", 10900, restaurant);
    orderFood = OrderFood.builder()
        .quantity(3)
        .order(new Order())
        .food(food)
        .build();
  }

  @Test
  public void 수량_음식금액_계산_정상() {
    assertEquals(orderFood.caculationPrice(food.getPrice()), 32700);
  }
}