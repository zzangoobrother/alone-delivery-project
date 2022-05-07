package com.example.alonedeliveryproject.web.advice;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResult {

  private int code;
  private String message;
}
