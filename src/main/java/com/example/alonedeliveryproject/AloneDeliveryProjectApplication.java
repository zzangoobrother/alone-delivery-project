package com.example.alonedeliveryproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class AloneDeliveryProjectApplication {

  public static void main(String[] args) {
    SpringApplication.run(AloneDeliveryProjectApplication.class, args);
  }

}
