package com.example.alonedeliveryproject.domain.restaurant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Restaurant {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "restaurant_id")
  private Long id;

  @Column(unique = true)
  private String name;

  @Column(name = "min_order_price")
  private int minOrderPrice;

  @Column(name = "delivery_fee")
  private int deliveryFee;

  @Builder
  public Restaurant(String name, int minOrderPrice, int deliveryFee) {
    this.name = name;
    this.minOrderPrice = minOrderPrice;
    this.deliveryFee = deliveryFee;
  }

  public boolean checkMinPrice(int inputPrice) {
    return inputPrice < minOrderPrice;
  }
}
