package com.mikewoo.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableCircuitBreaker
public class MicroserviceOrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroserviceOrderServiceApplication.class, args);
    }

}

