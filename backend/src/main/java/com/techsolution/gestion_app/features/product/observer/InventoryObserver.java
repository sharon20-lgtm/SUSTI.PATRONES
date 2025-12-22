package com.techsolution.gestion_app.features.product.observer;

import com.techsolution.gestion_app.domain.entities.Product;

public interface InventoryObserver {
    void update(Product product);
}
