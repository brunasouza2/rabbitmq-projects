package com.example.direct.routing.publisher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DirectRoutingPublisherApplication {

    public static void main(String[] args) {
        SpringApplication.run(DirectRoutingPublisherApplication.class, args);

        System.out.println("********* START DIRECT ROUTING PUBLISHER ********* ");

        DirectRoutingPublisherProgram program = new DirectRoutingPublisherProgram();
        program.init();

    }

}
