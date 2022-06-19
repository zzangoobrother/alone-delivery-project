package com.example.alonedeliveryproject.domain.food.repository;

import static com.example.alonedeliveryproject.domain.food.QFood.food;

import com.example.alonedeliveryproject.domain.food.Food;
import com.example.alonedeliveryproject.domain.restaurant.Restaurant;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import javax.persistence.EntityManager;

public class FoodRepositoryImpl implements FoodRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  public FoodRepositoryImpl(EntityManager em) {
    this.queryFactory = new JPAQueryFactory(em);
  }

  @Override
  public List<Food> findByRestaurantAndNameIn(Restaurant restaurant, List<String> names) {
    return queryFactory
        .select(food)
        .from(food)
        .where(
            restaurantEq(restaurant),
            food.name.in(names)
        )
        .fetch();
  }

  private BooleanExpression restaurantEq(Restaurant restaurant) {
    return food.restaurant.eq(restaurant);
  }

  @Override
  public List<Food> findByRestaurant(Restaurant restaurant) {
    return null;
  }
}
