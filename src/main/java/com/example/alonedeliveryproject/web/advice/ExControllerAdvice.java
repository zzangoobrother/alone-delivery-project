package com.example.alonedeliveryproject.web.advice;

import com.example.alonedeliveryproject.web.restaurant.exception.RestaurantException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice("com.example.alonedeliveryproject.web")
public class ExControllerAdvice {

  @ExceptionHandler
  public ResponseEntity<ErrorResult> restaurantExHandler(RestaurantException e) {
    ErrorResult errorResult = new ErrorResult(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
  }
}
