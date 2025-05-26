package com.trading.platform.evaexchange;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class EvaExchangeApplication {

    public static void main(String[] args) {
        SpringApplication.run(EvaExchangeApplication.class, args);
    }

}
