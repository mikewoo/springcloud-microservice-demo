package com.mikewoo.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
public class MicroserviceZuulGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroserviceZuulGatewayApplication.class, args);
    }

}

