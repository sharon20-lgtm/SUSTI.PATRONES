package com.techsolution.gestion_app.features.payment.adapter;

import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private PaymentGateway gateway;

    public void setGateway(PaymentGateway gateway) {
        this.gateway = gateway;
    }

    public boolean processPayment(double amount) {
        if (gateway == null) {
            throw new IllegalStateException("No se ha configurado la pasarela de pago.");
        }
        return gateway.pay(amount);
    }
}
