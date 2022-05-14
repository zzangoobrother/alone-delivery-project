package com.example.alonedeliveryproject.web.food.controller;

import com.example.alonedeliveryproject.web.food.dto.FoodDto.Response;
import com.example.alonedeliveryproject.web.food.dto.FoodSaveDtos;
import com.example.alonedeliveryproject.web.food.exception.FoodException;
import com.example.alonedeliveryproject.web.food.service.FoodService;
import java.util.List;
import java.util.Objects;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class FoodController {

  private final FoodService foodService;

  @PostMapping("/restaurant/{restaurantId}/food/register")
  public ResponseEntity<Void> save(@PathVariable long restaurantId, @RequestBody @Valid FoodSaveDtos foodSaveDtos, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      throw new FoodException(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
    }
    foodService.save(restaurantId, foodSaveDtos);

    return ResponseEntity.ok().build();
  }

  @GetMapping("/restaurant/{restaurantId}/food")
  public ResponseEntity<List<Response>> getFoods(@PathVariable long restaurantId) {
    return ResponseEntity.ok(foodService.getFoods(restaurantId));
  }
}
