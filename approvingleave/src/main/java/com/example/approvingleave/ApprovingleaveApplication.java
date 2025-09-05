package com.example.approvingleave;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRabbit
@SpringBootApplication
public class ApprovingleaveApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApprovingleaveApplication.class, args);
    }

}
