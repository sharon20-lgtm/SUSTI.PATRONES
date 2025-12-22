package com.techsolution.gestion_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.techsolution.gestion_app.domain.entities.StockAlert;
import java.util.List;

public interface StockAlertRepository extends JpaRepository<StockAlert, Long> {
    List<StockAlert> findByProductId(Long productId);
}
