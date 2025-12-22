package com.techsolution.gestion_app.integration;

import com.techsolution.gestion_app.controller.dto.PaymentRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PaymentControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void disabledGatewayRejectsPaymentAndEnableAccepts() {
        // disable paypal
        ResponseEntity<String> resDisable = restTemplate.postForEntity("/payments/config/paypal/disable", null, String.class);
        assertEquals(HttpStatus.OK, resDisable.getStatusCode());

        PaymentRequest req = new PaymentRequest();
        req.setMonto(100);
        req.setMetodo("paypal");

        ResponseEntity<String> resPay = restTemplate.postForEntity("/payments/order/1", req, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, resPay.getStatusCode());
        assertTrue(resPay.getBody().contains("deshabilitada"));

        // enable paypal
        ResponseEntity<String> resEnable = restTemplate.postForEntity("/payments/config/paypal/enable", null, String.class);
        assertEquals(HttpStatus.OK, resEnable.getStatusCode());

        ResponseEntity<String> resPay2 = restTemplate.postForEntity("/payments/order/1", req, String.class);
        assertEquals(HttpStatus.OK, resPay2.getStatusCode());
        assertTrue(resPay2.getBody().contains("Pago realizado con éxito"));
    }
}
