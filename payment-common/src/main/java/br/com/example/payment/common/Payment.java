package br.com.example.payment.common;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Payment implements Serializable {
    //classe pagamento
    private double amountToPay;
    private String CardNumber;
    private String Name;
}
