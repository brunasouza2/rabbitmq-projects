package com.example.workerqueueproducer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WorkerQueueProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(WorkerQueueProducerApplication.class, args);

        System.out.println("********* INICIANDO WORKER QUEUE PRODUCER ********* ");

        WorkerQueueProducerProgram program = new WorkerQueueProducerProgram();
        program.init();
    }

}
