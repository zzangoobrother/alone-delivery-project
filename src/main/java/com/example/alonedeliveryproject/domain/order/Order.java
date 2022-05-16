package com.example.alonedeliveryproject.domain.order;

import com.example.alonedeliveryproject.domain.food.Food;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Order {

  @Id
  @GeneratedValue
  @Column(name = "order_id")
  private Long id;

  private int quantity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "food_id")
  private Food food;

  @Builder
  public Order(int quantity, Food food) {
    this.quantity = quantity;
    this.food = food;
  }
}
