package com.example.requestingleave;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRabbit
@SpringBootApplication
public class RequestingleaveApplication {

    public static void main(String[] args) {
        SpringApplication.run(RequestingleaveApplication.class, args);
    }

}
