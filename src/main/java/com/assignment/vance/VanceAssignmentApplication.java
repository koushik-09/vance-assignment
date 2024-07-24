package com.assignment.vance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class VanceAssignmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(VanceAssignmentApplication.class, args);
    }

}
