package com.techsolution.gestion_app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.techsolution.gestion_app.domain.entities.Order;
import com.techsolution.gestion_app.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> create(@RequestBody Order order) {
        return ResponseEntity.ok(orderService.createOrder(order));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> get(@PathVariable Long id) {
        return orderService.getOrderById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{orderId}/descuento")
    public ResponseEntity<Order> applyDiscount(@PathVariable Long orderId, @RequestParam double porcentaje) {
        try {
            orderService.applyDiscount(orderId, porcentaje);
            return orderService.getOrderById(orderId).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{orderId}/restaurar")
    public ResponseEntity<Void> restore(@PathVariable Long orderId) {
        var opt = orderService.getOrderById(orderId);
        if (opt.isEmpty()) return ResponseEntity.notFound().build();
        orderService.restoreLastState(opt.get());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{orderId}/cancelar")
    public ResponseEntity<Void> cancel(@PathVariable Long orderId) {
        try {
            orderService.cancelOrder(orderId);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
