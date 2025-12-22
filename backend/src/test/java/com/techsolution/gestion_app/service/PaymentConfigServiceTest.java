package com.techsolution.gestion_app.service;

import com.techsolution.gestion_app.domain.entities.PaymentConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PaymentConfigServiceTest {

    @Autowired
    private PaymentConfigService paymentConfigService;

    @Test
    void getConfigCreatesDefaultIfMissing() {
        PaymentConfig c = paymentConfigService.getConfig();
        assertNotNull(c);
        assertTrue(c.isPaypalActivo());
        assertTrue(c.isYapeActivo());
        assertTrue(c.isPlinActivo());
    }

    @Test
    void updateConfigPersistsChanges() {
        paymentConfigService.updateConfig(false, true, false);
        PaymentConfig c = paymentConfigService.getConfig();
        assertFalse(c.isPaypalActivo());
        assertTrue(c.isYapeActivo());
        assertFalse(c.isPlinActivo());
    }
}
