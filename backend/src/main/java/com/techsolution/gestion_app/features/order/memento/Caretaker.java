package com.techsolution.gestion_app.features.order.memento;

import java.util.Stack;

public class Caretaker {

    private final Stack<OrderMemento> history = new Stack<>();

    public void addMemento(OrderMemento memento) { history.push(memento); }

    public OrderMemento undo() {
        if (!history.isEmpty()) return history.pop();
        return null;
    }
}
