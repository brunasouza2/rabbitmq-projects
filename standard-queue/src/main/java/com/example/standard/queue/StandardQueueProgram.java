package com.example.standard.queue;

import br.com.example.payment.common.ObjectSerialize;
import br.com.example.payment.common.Payment;
import com.example.standard.queue.util.ConnectionRabbitMQ;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

class StandardQueueProgram {

    private Channel channel = null;
    private final String QUEUE_NAME = "StandardQueue_ExampleQueue";

    void init() {
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

        receive();
    }

    private void createConnection(){
        try {
            ConnectionFactory factory = ConnectionRabbitMQ.getConnectionFactory();
            Connection connection = factory.newConnection();
            channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, true, false, false, null); //declaramos a fila, nome, fila durável

        }catch (IOException | TimeoutException ex){
            System.err.println("Não foi posssível criar conexão\n");
        }
    }

    private void sendMessage(Payment payment){
        try {
            channel.basicPublish("", QUEUE_NAME, null, ObjectSerialize.getInstance().serialize(payment));
            System.out.println(" [x] Payment Message Sent: {"+payment+"}"); //chamada para buplicação básica
        }catch (IOException ex){
            System.err.println("Não foi posssível enviar mensagem\n");
        }
    }

    private void receive(){
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

            channel.basicConsume(QUEUE_NAME, true, consumer);
        }catch (IOException ex){
            System.err.println("Não foi posssível receber mensagens\n");
        }
    }
}
