package com.example.alonedeliveryproject.web.order.dto;

import static java.util.stream.Collectors.toList;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderFoods {

  @Valid
  @NotNull
  List<OrderDtoRequest> orderFoods;

  public List<Long> getFoodIds() {
    return orderFoods.stream().map(OrderDtoRequest::getId).collect(toList());
  }
}
