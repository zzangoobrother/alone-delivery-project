package com.example.alonedeliveryproject.domain.food;

import com.example.alonedeliveryproject.domain.restaurant.Restaurant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Food {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "food_id")
  private Long id;

  private String name;

  private int price;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "restaurant_id")
  private Restaurant restaurant;

  @Builder
  public Food(String name, int price, Restaurant restaurant) {
    this.name = name;
    this.price = price;
    this.restaurant = restaurant;
  }

  public void addRestaurant(Restaurant restaurant) {
    this.restaurant = restaurant;
  }

  public void changeRestaurant(Restaurant restaurant) {
    this.restaurant = restaurant;
  }
}
