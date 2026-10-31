package com.example.CharityProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
@EnableDiscoveryClient
public class CharityProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(CharityProjectApplication.class, args);
	}

}
