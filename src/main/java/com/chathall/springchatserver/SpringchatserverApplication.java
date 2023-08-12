package com.chathall.springchatserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableMongoRepositories
@EnableWebSecurity
@SpringBootApplication
public class SpringchatserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringchatserverApplication.class, args);
	}

}
