package com.example.alonedeliveryproject.domain.order.repository;

import com.example.alonedeliveryproject.domain.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
