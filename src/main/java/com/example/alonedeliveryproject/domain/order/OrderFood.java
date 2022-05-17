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
public class OrderFood {

  @Id
  @GeneratedValue
  @Column(name = "order_food_id")
  private Long id;

  private int quantity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id")
  private Order order;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "food_id")
  private Food food;

  @Builder
  public OrderFood(int quantity, Order order, Food food) {
    this.quantity = quantity;
    this.order = order;
    this.food = food;
  }
}
