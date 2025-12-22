package com.techsolution.gestion_app.service;

import org.springframework.stereotype.Service;

import com.techsolution.gestion_app.domain.entities.Product;
import com.techsolution.gestion_app.features.product.observer.ProductSubject;
import com.techsolution.gestion_app.features.product.observer.ManagerObserver;
import com.techsolution.gestion_app.features.product.observer.PurchaseObserver;

import jakarta.annotation.PostConstruct;

@Service
public class StockObserverService {

    private final ProductSubject subject;
    private final StockAlertService stockAlertService;

    public StockObserverService(ProductSubject subject, StockAlertService stockAlertService) {
        this.subject = subject;
        this.stockAlertService = stockAlertService;
    }

    @PostConstruct
    public void init() {
        subject.attach(new ManagerObserver());
        subject.attach(new PurchaseObserver());
    }

    public void checkStock(Product product) {
        subject.notifyObservers(product);
        // además de notificar, persistimos una alerta cuando procede
        if (product.getStock() < product.getStockMinimo()) {
            stockAlertService.createAlert(product);
        }
    }
}
