package com.example.standard.queue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StandardQueueApplication {

    public static void main(String[] args) {
        SpringApplication.run(StandardQueueApplication.class, args);

        System.out.println("********* START STANDARD QUEUE ********* ");

        StandardQueueProgram program = new StandardQueueProgram();
        program.init();
    }

}
