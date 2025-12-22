package com.techsolution.gestion_app.service;

import org.springframework.stereotype.Service;

import com.techsolution.gestion_app.domain.entities.PaymentConfig;
import com.techsolution.gestion_app.repository.PaymentConfigRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentConfigService {

    private final PaymentConfigRepository repository;

    public PaymentConfig getConfig() {
        return repository.findById(1L).orElseGet(() -> repository.save(new PaymentConfig()));
    }

    public PaymentConfig updateConfig(boolean paypal, boolean yape, boolean plin) {
        PaymentConfig config = getConfig();
        config.setPaypalActivo(paypal);
        config.setYapeActivo(yape);
        config.setPlinActivo(plin);
        return repository.save(config);
    }
}
