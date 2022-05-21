package com.example.alonedeliveryproject.web.order.controller;

import com.example.alonedeliveryproject.web.order.dto.OrderDtoResponse;
import com.example.alonedeliveryproject.web.order.dto.OrderFoods;
import com.example.alonedeliveryproject.web.order.service.OrderService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class OrderController {

  private final OrderService orderService;

  @PostMapping("/orders/{restaurantId}")
  public OrderDtoResponse save(@PathVariable Long restaurantId, @RequestBody @Valid OrderFoods orderFoods) {
    return orderService.save(restaurantId, orderFoods.getOrderFoods());
  }

  @GetMapping("/orders/{orderId}")
  public OrderDtoResponse getOrders(@PathVariable Long orderId) {
    return orderService.getOrders(orderId);
  }
}
