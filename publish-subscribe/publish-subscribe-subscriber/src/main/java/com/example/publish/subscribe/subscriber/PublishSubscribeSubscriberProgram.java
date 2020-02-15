package com.example.publish.subscribe.subscriber;

import br.com.example.payment.common.ObjectSerialize;
import br.com.example.payment.common.Payment;
import com.example.publish.subscribe.subscriber.util.ConnectionRabbitMQ;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

class PublishSubscribeSubscriberProgram {

    private Channel channel = null;
    private final String EXCHANGE_NAME = "PublishSubscribe_Exchange";

    void init(){
        createConnection();
        receive();
    }

    private void createConnection(){
        try {
            ConnectionFactory factory = ConnectionRabbitMQ.getConnectionFactory();
            Connection connection = factory.newConnection();
            channel = connection.createChannel();
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
                    System.out.println(" [x] Received : {"+payment+"}");
                }
            };

            String queueName = declareAndBindQueueToExchange();
            channel.basicConsume(queueName, true, consumer);
        }catch (IOException ex){
            System.err.println("Não foi posssível receber mensagens\n");
        }
    }

    private String declareAndBindQueueToExchange() {
        try {
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
            String queueName = channel.queueDeclare().getQueue();
            channel.queueBind(queueName, EXCHANGE_NAME, "");
            return queueName;
        }catch (Exception ex){
            System.err.println("Não foi posssível obter Queue Name\n");
        }
        return null;
    }
}
