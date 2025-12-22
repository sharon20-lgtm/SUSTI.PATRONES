package com.techsolution.gestion_app.features.product.observer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.techsolution.gestion_app.domain.entities.Product;

@Component
public class ProductSubject implements Subject {

    private final List<InventoryObserver> observers = new ArrayList<>();

    @Override
    public void attach(InventoryObserver observer) {
        observers.add(observer);
    }

    @Override
    public void detach(InventoryObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        // noop
    }

    public void notifyObservers(Product product) {
        if (product.getStock() < product.getStockMinimo()) {
            for (InventoryObserver obs : observers) {
                obs.update(product);
            }
        }
    }
}
