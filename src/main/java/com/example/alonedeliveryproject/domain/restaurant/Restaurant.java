package com.example.alonedeliveryproject.domain.restaurant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Restaurant {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "restaurant_id")
  private Long id;

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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Restaurant that = (Restaurant) o;

    if (minOrderPrice != that.minOrderPrice) {
      return false;
    }
    if (deliveryFee != that.deliveryFee) {
      return false;
    }
    if (id != null ? !id.equals(that.id) : that.id != null) {
      return false;
    }
    return name != null ? name.equals(that.name) : that.name == null;
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (name != null ? name.hashCode() : 0);
    result = 31 * result + minOrderPrice;
    result = 31 * result + deliveryFee;
    return result;
  }
}
