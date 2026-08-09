package com.duong.backendservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class BackendServiceApplication {

    static void main(String[] args) {
        SpringApplication.run(BackendServiceApplication.class, args);
    }

}
