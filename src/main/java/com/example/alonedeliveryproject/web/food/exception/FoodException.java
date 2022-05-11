package com.example.alonedeliveryproject.web.food.exception;

public class FoodException extends RuntimeException {

  public FoodException() {
  }

  public FoodException(String message) {
    super(message);
  }

  public FoodException(String message, Throwable cause) {
    super(message, cause);
  }

  public FoodException(Throwable cause) {
    super(cause);
  }

  public FoodException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
