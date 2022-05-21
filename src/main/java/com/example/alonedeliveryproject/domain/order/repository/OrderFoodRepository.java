package com.example.alonedeliveryproject.domain.order.repository;

import com.example.alonedeliveryproject.domain.order.Order;
import com.example.alonedeliveryproject.domain.order.OrderFood;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderFoodRepository extends JpaRepository<OrderFood, Long> {

  List<OrderFood> findAllByOrder(Order order);
}
