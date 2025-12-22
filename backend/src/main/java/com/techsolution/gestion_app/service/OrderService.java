package com.techsolution.gestion_app.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.techsolution.gestion_app.domain.entities.Order;
import com.techsolution.gestion_app.domain.enums.OrderStatus;
import com.techsolution.gestion_app.repository.OrderRepository;
import com.techsolution.gestion_app.features.order.memento.Caretaker;
import com.techsolution.gestion_app.features.order.memento.OrderMemento;
import com.techsolution.gestion_app.features.order.memento.OrderOriginator;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    // Memento
    private final OrderOriginator originator = new OrderOriginator();
    private final Caretaker caretaker = new Caretaker();

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    @Transactional
    public Order createOrder(Order order) {
        order.setStatus(OrderStatus.PENDIENTE);
        order.setOrderDate(LocalDateTime.now());
        double total = 0.0;
        if (order.getOrderItems() != null) {
            for (var item : order.getOrderItems()) {
                total += item.getUnitPrice() * item.getQuantity();
                item.setOrder(order);
            }
        }
        order.setTotalAmount(total);

        Order saved = orderRepository.save(order);

        // save initial state
        originator.setOrder(saved);
        caretaker.addMemento(originator.saveState());

        return saved;
    }

    @Transactional
    public void applyDiscount(Long orderId, double porcentaje) {
        Order o = orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("Orden no existe"));
        // save state before change
        originator.setOrder(o);
        caretaker.addMemento(originator.saveState());

        double descuento = o.getTotalAmount() * (porcentaje / 100.0);
        o.setTotalAmount(o.getTotalAmount() - descuento);
        orderRepository.save(o);
    }

    @Transactional
    public void cancelOrder(Long orderId) {
        Order o = orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("Orden no existe"));
        originator.setOrder(o);
        caretaker.addMemento(originator.saveState());

        o.setStatus(OrderStatus.CANCELADO);
        orderRepository.save(o);
    }

    public void restoreLastState(Order order) {
        OrderMemento memento = caretaker.undo();
        if (memento != null) {
            originator.setOrder(order);
            originator.restoreState(memento);
            orderRepository.save(order);
        }
    }
}
