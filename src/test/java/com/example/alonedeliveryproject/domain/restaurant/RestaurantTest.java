package com.example.alonedeliveryproject.domain.restaurant;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RestaurantTest {

  private Restaurant restaurant;

  @Before
  public void setup() {
    restaurant = Restaurant.builder()
        .name("쉑쉑 강남점")
        .minOrderPrice(100_000)
        .deliveryFee(10_000)
        .build();
  }

  @Test
  public void 최소주문금액_주문금액_비교_주문금액이_큼() {
    assertFalse(restaurant.checkMinPrice(100100));
  }

  @Test
  public void 최소주문금액_주문금액_비교_주문금액이_작음() {
    assertTrue(restaurant.checkMinPrice(99000));
  }
}