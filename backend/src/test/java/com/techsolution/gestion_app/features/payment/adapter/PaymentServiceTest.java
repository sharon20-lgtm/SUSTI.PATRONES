package com.techsolution.gestion_app.features.payment.adapter;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class PaymentServiceTest {

    @Test
    void processPaymentThrowsWhenGatewayNotSet() {
        PaymentService s = new PaymentService();
        Exception ex = assertThrows(IllegalStateException.class, () -> s.processPayment(100));
        assertTrue(ex.getMessage().contains("No se ha configurado"));
    }

    @Test
    void processPaymentUsesGateway() {
        PaymentService s = new PaymentService();
        PaymentGateway gw = Mockito.mock(PaymentGateway.class);
        Mockito.when(gw.pay(150.0)).thenReturn(true);
        s.setGateway(gw);
        assertTrue(s.processPayment(150.0));
        Mockito.verify(gw).pay(150.0);
    }
}
