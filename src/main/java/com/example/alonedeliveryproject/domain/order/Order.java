package com.example.alonedeliveryproject.domain.order;

import com.example.alonedeliveryproject.domain.BaseTimeEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Order extends BaseTimeEntity {

  @Id
  @GeneratedValue
  @Column(name = "order_id")
  private Long id;
}
