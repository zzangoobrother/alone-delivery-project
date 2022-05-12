package com.example.alonedeliveryproject.web.restaurant.controller;

import com.example.alonedeliveryproject.web.restaurant.dto.RestaurantDto;
import com.example.alonedeliveryproject.web.restaurant.dto.RestaurantDto.Response;
import com.example.alonedeliveryproject.web.restaurant.exception.RestaurantException;
import com.example.alonedeliveryproject.web.restaurant.service.RestaurantService;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class RestaurantController {

  private final RestaurantService restaurantService;

  @PostMapping("/restaurant/register")
  public Long save(@RequestBody @Validated RestaurantDto.Request request, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      throw new RestaurantException(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
    }
    return restaurantService.restaurantSave(request).getId();
  }

  @GetMapping("/restaurants")
  public ResponseEntity<List<Response>> getRestaurants() {
    return ResponseEntity.ok().body(restaurantService.getRestaurants());
  }
}
