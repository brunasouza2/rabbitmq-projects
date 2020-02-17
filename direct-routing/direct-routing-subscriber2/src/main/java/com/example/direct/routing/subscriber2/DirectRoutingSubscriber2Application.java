package com.example.direct.routing.subscriber2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DirectRoutingSubscriber2Application {

    public static void main(String[] args) {
        SpringApplication.run(DirectRoutingSubscriber2Application.class, args);

        System.out.println("********* START DIRECT ROUTING SUBSCRIBER 2 ********* ");

        DirectRoutingSubscriber2Program program = new DirectRoutingSubscriber2Program();
        program.init();
    }

}
