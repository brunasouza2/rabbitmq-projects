package com.example.workerqueueconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WorkerQueueConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(WorkerQueueConsumerApplication.class, args);

        System.out.println("********* START WORKER QUEUE CONSUMER ********* ");

        WorkerQueueConsumerProgram program = new WorkerQueueConsumerProgram();
        program.init();
    }

}
