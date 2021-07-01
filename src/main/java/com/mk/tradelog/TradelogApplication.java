package com.mk.tradelog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class TradelogApplication {

    public static void main(String[] args) {
        SpringApplication.run(TradelogApplication.class, args);
    }

}
