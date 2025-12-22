package com.techsolution.gestion_app.features.payment.adapter;

public interface PaymentGateway {
    boolean pay(double amount);
}
