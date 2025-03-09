package com.circuit_breaker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableMongoAuditing
@EnableMongoRepositories
@EnableConfigurationProperties
public class CircuitBreakerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CircuitBreakerApplication.class, args);
    }

}
