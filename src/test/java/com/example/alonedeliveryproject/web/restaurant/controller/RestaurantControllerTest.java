package com.example.alonedeliveryproject.web.restaurant.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.alonedeliveryproject.domain.restaurant.Restaurant;
import com.example.alonedeliveryproject.web.restaurant.dto.RestaurantDto;
import com.example.alonedeliveryproject.web.restaurant.dto.RestaurantDto.Request;
import com.example.alonedeliveryproject.web.restaurant.dto.RestaurantDto.Response;
import com.example.alonedeliveryproject.web.restaurant.service.RestaurantService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

@WebMvcTest(RestaurantController.class)
class RestaurantControllerTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private RestaurantService restaurantService;

  @Test
  void 음식점_등록() throws Exception {
    given(restaurantService.restaurantSave(any()))
        .willReturn(Restaurant.builder()
                .name("쉑쉑 강남점")
                .minOrderPrice(1_000)
                .deliveryFee(0)
                .build());

    RestaurantDto.Request request = Request.builder()
        .name("쉑쉑 강남점")
        .minOrderPrice(1_000)
        .deliveryFee(0)
        .build();

    mvc.perform(post("/restaurant/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(request)))
        .andExpect(status().isOk());
  }

  @Test
  void 최소주문_가격_1000원_미만_에러() throws Exception {
    RestaurantDto.Request request = Request.builder()
        .name("쉑쉑 강남점")
        .minOrderPrice(500)
        .deliveryFee(10_000)
        .build();

    MockHttpServletRequestBuilder builder = post("/restaurant/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(request));

    ResultActions resultActions = mvc.perform(builder).andExpect(status().isBadRequest());
    MvcResult mvcResult = resultActions.andReturn();

    assertThat(mvcResult.getResolvedException().getMessage()).isEqualTo("최소 주문 금액은 1,000원 입니다.");
  }

  @Test
  void 초과주문_가격_100000원_초과_에러() throws Exception {
    RestaurantDto.Request request = Request.builder()
        .name("쉑쉑 강남점")
        .minOrderPrice(100100)
        .deliveryFee(1000)
        .build();

    MockHttpServletRequestBuilder builder = post("/restaurant/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(request));

    ResultActions resultActions = mvc.perform(builder).andExpect(status().isBadRequest());
    MvcResult mvcResult = resultActions.andReturn();

    assertThat(mvcResult.getResolvedException().getMessage()).isEqualTo("최대 주문 금액은 100,000원 입니다.");
  }

  @Test
  void 배달비_0원_미만_에러() throws Exception {
    RestaurantDto.Request request = Request.builder()
        .name("쉑쉑 강남점")
        .minOrderPrice(5000)
        .deliveryFee(-500)
        .build();

    MockHttpServletRequestBuilder builder = post("/restaurant/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(request));

    ResultActions resultActions = mvc.perform(builder).andExpect(status().isBadRequest());
    MvcResult mvcResult = resultActions.andReturn();

    assertThat(mvcResult.getResolvedException().getMessage()).isEqualTo("최소 배달비 금액은 0원 입니다.");
  }

  @Test
  void 배달비_10000원_초과_에러() throws Exception {
    RestaurantDto.Request request = Request.builder()
        .name("쉑쉑 강남점")
        .minOrderPrice(5000)
        .deliveryFee(10100)
        .build();

    MockHttpServletRequestBuilder builder = post("/restaurant/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(request));

    ResultActions resultActions = mvc.perform(builder).andExpect(status().isBadRequest());
    MvcResult mvcResult = resultActions.andReturn();

    assertThat(mvcResult.getResolvedException().getMessage()).isEqualTo("최대 배달비 금액은 10,000원 입니다.");
  }

  @Test
  void 음식점_전체_조회() throws Exception {
    given(restaurantService.getRestaurants())
        .willReturn(Arrays.asList(Response.builder()
                        .name("쉑쉑 강남점")
                        .minOrderPrice(100_000)
                        .deliveryFee(10_000)
                        .build()));

    mvc.perform(get("/restaurants"))
        .andExpect(status().isOk());
  }
}