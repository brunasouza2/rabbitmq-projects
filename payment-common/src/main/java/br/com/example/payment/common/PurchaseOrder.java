package br.com.example.payment.common;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class PurchaseOrder implements Serializable {
    // Classe de pedido de compra
    private double amountToPay; //montante a pagar
    private String poNumber; //numero da ordem de compra
    private String companyName; // nome da empresa
    private int paymentDayTerms; // termos do dia de pagamento
}
