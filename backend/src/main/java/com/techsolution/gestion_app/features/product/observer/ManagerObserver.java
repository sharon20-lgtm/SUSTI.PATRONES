package com.techsolution.gestion_app.features.product.observer;

import com.techsolution.gestion_app.domain.entities.Product;

public class ManagerObserver implements InventoryObserver {
    @Override
    public void update(Product product) {
        System.out.println("GERENTE alerta: El stock de " + product.getProducto() + " está bajo. Stock actual: " + product.getStock());
    }
}
