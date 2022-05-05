package com.example.alonedeliveryproject.domain.restaurant.Repository;

import com.example.alonedeliveryproject.domain.restaurant.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

}
