package com.example.workerqueueproducer;

import br.com.example.payment.common.ObjectSerialize;
import br.com.example.payment.common.Payment;
import com.example.workerqueueproducer.util.ConnectionRabbitMQ;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

class WorkerQueueProducerProgram {

    private Channel channel = null;
    private final String QUEUE_NAME = "WorkerQueue_Queue";

    void init(){
        Payment payment1 = new Payment(25.3,"123456789", "Sr. Maisel");
        Payment payment2 = new Payment(52,"123456789", "Sr. Maisel");
        Payment payment3 = new Payment(96.5,"123456789", "Sr. Maisel");
        Payment payment4 = new Payment(18.7,"123456789", "Sr. Maisel");
        Payment payment5 = new Payment(369.5,"123456789", "Sr. Maisel");
        Payment payment6 = new Payment(845.2,"123456789", "Sr. Maisel");
        Payment payment7 = new Payment(78.5,"123456789", "Sr. Maisel");
        Payment payment8 = new Payment(92.2,"123456789", "Sr. Maisel");
        Payment payment9 = new Payment(14.1,"123456789", "Sr. Maisel");
        Payment payment10 = new Payment(58.4,"123456789", "Sr. Maisel");

        createConnection();

        sendMessage(payment1);
        sendMessage(payment2);
        sendMessage(payment3);
        sendMessage(payment4);
        sendMessage(payment5);
        sendMessage(payment6);
        sendMessage(payment7);
        sendMessage(payment8);
        sendMessage(payment9);
        sendMessage(payment10);
    }

    private void createConnection(){
        try {
            ConnectionFactory factory = ConnectionRabbitMQ.getConnectionFactory();
            Connection connection = factory.newConnection();
            channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);

        }catch (IOException | TimeoutException ex){
            System.err.println("Não foi posssível criar conexão\n");
        }
    }

    private void sendMessage(Payment payment){
        try {
            channel.basicPublish("", QUEUE_NAME, null, ObjectSerialize.getInstance().serialize(payment));
            System.out.println(" [x] Payment Message Sent: {"+payment.getCardNumber()+"} {"+payment.getAmountToPay()+"} {"+payment.getName()+"}"); //chamada para buplicação básica
        }catch (IOException ex){
            System.err.println("Não foi posssível enviar mensagem\n");
        }
    }
}
