package com.techsolution.gestion_app.features.payment.adapter;

import org.springframework.stereotype.Component;

@Component
public class PayPalAdapter implements PaymentGateway {

    private boolean enabled = true;

    @Override
    public boolean pay(double amount) {
        if (!enabled) {
            System.out.println("PayPal está deshabilitado.");
            return false;
        }
        System.out.println("Procesando pago por PayPal: " + amount);
        return true;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }
}
