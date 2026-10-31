package com.ecom.actionchariteservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ActionchariteServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActionchariteServiceApplication.class, args);
    }

}
