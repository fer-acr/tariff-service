package com.itx.tariffservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class TariffServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(TariffServiceApplication.class, args);
  }

}
