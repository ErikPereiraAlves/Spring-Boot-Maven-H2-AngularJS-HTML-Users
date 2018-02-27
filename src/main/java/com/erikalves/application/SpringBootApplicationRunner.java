package com.erikalves.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@Configuration
@EnableAutoConfiguration
@EnableJpaRepositories
@SpringBootApplication(scanBasePackages={"com.erikalves.application"})
class SpringBootApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootApplicationRunner.class, args);
    }
}