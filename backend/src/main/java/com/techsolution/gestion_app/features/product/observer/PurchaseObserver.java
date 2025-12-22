package com.techsolution.gestion_app.features.product.observer;

import com.techsolution.gestion_app.domain.entities.Product;

public class PurchaseObserver implements InventoryObserver {
    @Override
    public void update(Product product) {
        System.out.println("COMPRAS alerta: El stock de " + product.getProducto() + " está bajo. Stock actual: " + product.getStock());
    }
}
