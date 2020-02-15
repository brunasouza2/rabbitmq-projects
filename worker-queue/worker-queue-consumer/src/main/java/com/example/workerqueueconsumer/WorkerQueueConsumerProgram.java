package com.example.workerqueueconsumer;

import br.com.example.payment.common.ObjectSerialize;
import br.com.example.payment.common.Payment;
import com.example.workerqueueconsumer.util.ConnectionRabbitMq;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class WorkerQueueConsumerProgram {

    private Channel channel = null;
    private final String QUEUE_NAME = "WorkerQueue_Queue";

    void init(){
        createConnection();
        receive();
    }

    private void createConnection(){
        try {
            ConnectionFactory factory = ConnectionRabbitMq.getConnectionFactory();
            Connection connection = factory.newConnection();
            channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            channel.basicQos(0,1, false); // reconhecimento de mensagens, quando tiramos da fila fazemos uma de cada vez
        }catch (IOException | TimeoutException ex){
            System.err.println("Não foi posssível criar conexão\n");
        }
    }

    private void receive() {
        try {
            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                        throws IOException {
                    String message = new String(body, "UTF-8");

                    Payment payment = (Payment) ObjectSerialize.getInstance().deSerialize(message.getBytes(),Payment.class);
                    System.out.println(" [x] Received : {"+payment.getCardNumber()+"} {"+payment.getAmountToPay()+"} {"+payment.getName()+"}");

                    channel.basicAck(envelope.getDeliveryTag(), false); //envio de confirmação ao RabbitMq , podendo descartá-la da fila.
                }
            };

            channel.basicConsume(QUEUE_NAME, false, consumer);
        }catch (IOException ex){
            System.err.println("Não foi posssível receber mensagens\n");
        }
    }
}
