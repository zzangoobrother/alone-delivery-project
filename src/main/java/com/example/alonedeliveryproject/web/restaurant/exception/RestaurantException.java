package com.example.alonedeliveryproject.web.restaurant.exception;

public class RestaurantException extends RuntimeException {

  public RestaurantException() {
    super();
  }

  public RestaurantException(String message) {
    super(message);
  }

  public RestaurantException(String message, Throwable cause) {
    super(message, cause);
  }

  public RestaurantException(Throwable cause) {
    super(cause);
  }

  public RestaurantException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
