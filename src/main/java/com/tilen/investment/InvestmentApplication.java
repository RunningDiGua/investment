package com.tilen.investment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class InvestmentApplication {

  public static void main(String[] args) throws IOException {
    SpringApplication.run(InvestmentApplication.class, args);
  }
}
