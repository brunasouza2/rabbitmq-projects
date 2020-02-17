package com.example.direct.routing.publisher;

import br.com.example.payment.common.ObjectSerialize;
import br.com.example.payment.common.Payment;
import br.com.example.payment.common.PurchaseOrder;
import com.example.publish.subscribe.publisher.util.ConnectionRabbitMQ;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

class DirectRoutingPublisherProgram {

    private Channel channel = null;
    private final String EXCHANGE_NAME = "DirectRouting_Exchange";
    private final String CARD_PAYMENT_QUEUE_NAME = "CardPaymentDirectRouting_Queue";
    private final String PURCHASE_ORDER_QUEUE_NAME = "PurchaseOrderDirectRouting_Queue";


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

        PurchaseOrder purchaseOrder1 = new PurchaseOrder(50.0, "147258369","Company A", 75);
        PurchaseOrder purchaseOrder2 = new PurchaseOrder(152.2, "598712541","Company B", 25);
        PurchaseOrder purchaseOrder3 = new PurchaseOrder(85.4, "5412316488","Company C", 45);
        PurchaseOrder purchaseOrder4 = new PurchaseOrder(96.7, "1212205445","Company D", 30);
        PurchaseOrder purchaseOrder5 = new PurchaseOrder(74.3, "8784125102","Company E", 75);
        PurchaseOrder purchaseOrder6 = new PurchaseOrder(25.1, "1451515488","Company F", 25);
        PurchaseOrder purchaseOrder7 = new PurchaseOrder(97.2, "8778841121","Company G", 90);
        PurchaseOrder purchaseOrder8 = new PurchaseOrder(636.1, "9891212154","Company H", 60);
        PurchaseOrder purchaseOrder9 = new PurchaseOrder(784.1, "7484152121","Company I", 35);
        PurchaseOrder purchaseOrder10 = new PurchaseOrder(841.5, "1541545477","Company J", 45);

        createConnection();

        sendPayment(payment1);
        sendPayment(payment2);
        sendPayment(payment3);
        sendPayment(payment4);
        sendPayment(payment5);
        sendPayment(payment6);
        sendPayment(payment7);
        sendPayment(payment8);
        sendPayment(payment9);
        sendPayment(payment10);

        sendPurchaseOrder(purchaseOrder1);
        sendPurchaseOrder(purchaseOrder2);
        sendPurchaseOrder(purchaseOrder3);
        sendPurchaseOrder(purchaseOrder4);
        sendPurchaseOrder(purchaseOrder5);
        sendPurchaseOrder(purchaseOrder6);
        sendPurchaseOrder(purchaseOrder7);
        sendPurchaseOrder(purchaseOrder8);
        sendPurchaseOrder(purchaseOrder9);
        sendPurchaseOrder(purchaseOrder10);
    }

    private void sendPayment(Payment payment) {
        sendMessage(ObjectSerialize.getInstance().serialize(payment), "CardPayment"); //CardPayment é a chave de roteamento
        System.out.println(" [x] Payment Message Sent: {"+payment+"}");
    }

    private void sendPurchaseOrder(PurchaseOrder purchaseOrder) {
        sendMessage(ObjectSerialize.getInstance().serialize(purchaseOrder), "PurchaseOrder");
        System.out.println(" [x] PurchaseOrder Message Sent: {"+purchaseOrder+"}");
    }

    private void sendMessage(byte[] message, String routingKey) {
        try {
            channel.basicPublish(EXCHANGE_NAME, routingKey, null, message);
        }catch (IOException ex){
            System.err.println("Não foi posssível enviar mensagem\n");
        }
    }

    private void createConnection() {
        try {
            ConnectionFactory factory = ConnectionRabbitMQ.getConnectionFactory();
            Connection connection = factory.newConnection();
            channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, "direct");

            channel.queueDeclare(CARD_PAYMENT_QUEUE_NAME, true, false, false, null);
            channel.queueDeclare(PURCHASE_ORDER_QUEUE_NAME, true, false, false, null);

            channel.queueBind(CARD_PAYMENT_QUEUE_NAME, EXCHANGE_NAME, "CardPayment");
            channel.queueBind(PURCHASE_ORDER_QUEUE_NAME, EXCHANGE_NAME, "PurchaseOrder");

        }catch (IOException | TimeoutException ex){
            System.err.println("Não foi posssível criar conexão\n");
        }
    }
}
