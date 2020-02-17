package com.example.direct.routing.subscriber1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DirectRoutingSubscriber1Application {

    public static void main(String[] args) {
        SpringApplication.run(DirectRoutingSubscriber1Application.class, args);

        System.out.println("********* START DIRECT ROUTING SUBSCRIBER 1 ********* ");

        DirectRoutingSubscriber1Program program = new DirectRoutingSubscriber1Program();
        program.init();
    }

}
