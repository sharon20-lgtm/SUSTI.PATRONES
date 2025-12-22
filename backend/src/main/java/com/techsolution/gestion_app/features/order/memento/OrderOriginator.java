package com.techsolution.gestion_app.features.order.memento;

import com.techsolution.gestion_app.domain.entities.Order;

public class OrderOriginator {

    private Order order;

    public void setOrder(Order order) { this.order = order; }

    public OrderMemento saveState() {
        return new OrderMemento(
                order.getId(),
                order.getStatus(),
                order.getTotalAmount(),
                order.getOrderDate()
        );
    }

    public void restoreState(OrderMemento memento) {
        order.setStatus(memento.getStatus());
        order.setTotalAmount(memento.getTotalAmount());
        order.setOrderDate(memento.getLastUpdate());
    }
}
