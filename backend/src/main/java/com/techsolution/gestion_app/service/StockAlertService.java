package com.techsolution.gestion_app.service;

import org.springframework.stereotype.Service;

import com.techsolution.gestion_app.domain.entities.Product;
import com.techsolution.gestion_app.domain.entities.StockAlert;
import com.techsolution.gestion_app.repository.StockAlertRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockAlertService {

    private final StockAlertRepository repository;

    public StockAlert createAlert(Product product) {
        StockAlert a = new StockAlert();
        a.setProductId(product.getId());
        a.setProductName(product.getProducto());
        a.setStock(product.getStock());
        return repository.save(a);
    }

    public List<StockAlert> getAll() {
        return repository.findAll();
    }

    public List<StockAlert> getByProduct(Long productId) {
        return repository.findByProductId(productId);
    }
}
