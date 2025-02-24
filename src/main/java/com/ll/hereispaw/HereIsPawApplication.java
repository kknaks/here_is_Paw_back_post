package com.ll.hereispaw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;

@SpringBootApplication
@EnableJdbcAuditing
public class HereIsPawApplication {

  public static void main(String[] args) {
    SpringApplication.run(HereIsPawApplication.class, args);
  }

}
