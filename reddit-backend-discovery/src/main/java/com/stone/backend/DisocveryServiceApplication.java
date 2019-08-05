package com.stone.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class DisocveryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DisocveryServiceApplication.class, args);
	}

}
