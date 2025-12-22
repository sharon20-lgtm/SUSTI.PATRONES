package com.techsolution.gestion_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.techsolution.gestion_app.domain.entities.PaymentConfig;

public interface PaymentConfigRepository extends JpaRepository<PaymentConfig, Long> {
}
