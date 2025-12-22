package com.techsolution.gestion_app.features.product.observer;

public interface Subject {
    void attach(InventoryObserver observer);
    void detach(InventoryObserver observer);
    void notifyObservers();
}
