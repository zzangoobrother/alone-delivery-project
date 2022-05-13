package com.example.alonedeliveryproject.web.food.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.alonedeliveryproject.web.food.dto.FoodDto.Request;
import com.example.alonedeliveryproject.web.food.dto.FoodSaveDtos;
import com.example.alonedeliveryproject.web.food.service.FoodService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

@WebMvcTest(FoodController.class)
class FoodControllerTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private FoodService foodService;

  private Request foodDtoRequest;

  @BeforeEach
  void setup() {
    foodDtoRequest = Request.builder()
        .name("쉑버거 더블")
        .price(10900)
        .build();
  }

  @Test
  void 음식_등록_정상() throws Exception {
    given(foodService.save(anyLong(), any())).willReturn(null);

    List<Request> foodsRequest = new ArrayList<>();
    foodsRequest.add(foodDtoRequest);

    mvc.perform(post("/restaurant/" + 1 + "/food/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(new FoodSaveDtos(foodsRequest))))
        .andExpect(status().isOk());
  }

  @Test
  void 최소음식_가격_100원_미만_에러() throws Exception {
    List<Request> foodsRequest = new ArrayList<>();
    foodsRequest.add(Request.builder()
        .name("쉑버거 더블")
        .price(10900)
        .build());

    foodsRequest.add(Request.builder()
        .name("쉑 치킨 버거")
        .price(0)
        .build());

    MockHttpServletRequestBuilder builder = post("/restaurant/" + 1 + "/food/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(new FoodSaveDtos(foodsRequest)));

    ResultActions resultActions = mvc.perform(builder).andExpect(status().isBadRequest());
    MvcResult mvcResult = resultActions.andReturn();

    assertThat(mvcResult.getResolvedException().getMessage()).isEqualTo("최소 음식 가격은 100원 입니다.");
  }

  @Test
  void 초과음식_가격_1000000원_초과_에러() throws Exception {
    List<Request> foodsRequest = new ArrayList<>();
    foodsRequest.add(Request.builder()
        .name("쉑버거 더블")
        .price(10900)
        .build());

    foodsRequest.add(Request.builder()
        .name("쉑 치킨 버거")
        .price(1000100)
        .build());

    MockHttpServletRequestBuilder builder = post("/restaurant/" + 1 + "/food/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(new FoodSaveDtos(foodsRequest)));

    ResultActions resultActions = mvc.perform(builder).andExpect(status().isBadRequest());
    MvcResult mvcResult = resultActions.andReturn();

    assertThat(mvcResult.getResolvedException().getMessage()).isEqualTo("최대 음식 가격은 1,000,000원 입니다.");
  }
}
