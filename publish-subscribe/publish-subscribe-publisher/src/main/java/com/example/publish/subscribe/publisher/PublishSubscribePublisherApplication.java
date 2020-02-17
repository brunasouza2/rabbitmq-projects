package com.example.publish.subscribe.publisher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PublishSubscribePublisherApplication {

    public static void main(String[] args) {
        SpringApplication.run(PublishSubscribePublisherApplication.class, args);

        System.out.println("********* START PUBLISH SUBSCRIBE PUBLISHER ********* ");

        PublishSubscribePublisherProgram program = new PublishSubscribePublisherProgram();
        program.init();
    }

}
