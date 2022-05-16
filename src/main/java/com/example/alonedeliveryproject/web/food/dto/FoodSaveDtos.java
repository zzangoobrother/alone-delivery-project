package com.example.alonedeliveryproject.web.food.dto;

import com.example.alonedeliveryproject.web.food.dto.FoodDto.Request;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FoodSaveDtos {

  @Valid
  @NotNull
  private List<Request> foodSaveDtos;


}
