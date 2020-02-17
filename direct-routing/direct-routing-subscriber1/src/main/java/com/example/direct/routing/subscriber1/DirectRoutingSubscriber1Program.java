package com.example.direct.routing.subscriber1;

import br.com.example.payment.common.ObjectSerialize;
import br.com.example.payment.common.Payment;
import com.example.publish.subscribe.publisher.util.ConnectionRabbitMQ;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

class DirectRoutingSubscriber1Program {

    private Channel channel = null;
    private final String EXCHANGE_NAME = "DirectRouting_Exchange";
    private final String CARD_PAYMENT_QUEUE_NAME = "CardPaymentDirectRouting_Queue";

    void init(){
        createConnection();
        receive();
    }

    private void createConnection() {
        try {
            ConnectionFactory factory = ConnectionRabbitMQ.getConnectionFactory();
            Connection connection = factory.newConnection();
            channel = connection.createChannel();

            channel.exchangeDeclare(EXCHANGE_NAME, "direct");
            channel.queueDeclare(CARD_PAYMENT_QUEUE_NAME, true, false, false, null);
            channel.queueBind(CARD_PAYMENT_QUEUE_NAME, EXCHANGE_NAME, "CardPayment");
            channel.basicQos(0,1,false);
        } catch (IOException | TimeoutException ex) {
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
                    String routingKey = envelope.getRoutingKey();
                    channel.basicAck(envelope.getDeliveryTag(), false);
                    Payment payment = (Payment) ObjectSerialize.getInstance().deSerialize(message.getBytes(), Payment.class);
                    System.out.println(" [x] Payment - Routing Key <"+routingKey+"> - Received : {" + payment + "}");
                }
            };

            channel.basicConsume(CARD_PAYMENT_QUEUE_NAME, false, consumer);
        } catch (IOException ex) {
            System.err.println("Não foi posssível receber mensagens\n");
        }
    }
}
