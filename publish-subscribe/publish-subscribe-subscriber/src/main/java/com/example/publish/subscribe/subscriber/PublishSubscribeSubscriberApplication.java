package com.example.publish.subscribe.subscriber;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PublishSubscribeSubscriberApplication {

    public static void main(String[] args) {
        SpringApplication.run(PublishSubscribeSubscriberApplication.class, args);
        System.out.println("********* START PUBLISH SUBSCRIBE SUBSCRIBER ********* ");

        PublishSubscribeSubscriberProgram program = new PublishSubscribeSubscriberProgram();
        program.init();
    }

}
