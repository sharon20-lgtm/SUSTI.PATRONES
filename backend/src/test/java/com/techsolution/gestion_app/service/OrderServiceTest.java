package com.techsolution.gestion_app.service;

import com.techsolution.gestion_app.domain.entities.Order;
import com.techsolution.gestion_app.domain.entities.OrderItem;
import com.techsolution.gestion_app.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class OrderServiceTest {

    private OrderRepository orderRepository;
    private OrderService orderService;

    @BeforeEach
    void setup() {
        orderRepository = Mockito.mock(OrderRepository.class);
        orderService = new OrderService(orderRepository);
    }

    @Test
    void createApplyDiscountAndRestore() {
        Order o = new Order();
        OrderItem it = new OrderItem();
        it.setProductId(1L);
        it.setQuantity(2);
        it.setUnitPrice(50.0);
        ArrayList<OrderItem> items = new ArrayList<>();
        items.add(it);
        o.setOrderItems(items);

        when(orderRepository.save(Mockito.any())).thenAnswer(i -> {
            Order arg = i.getArgument(0);
            if (arg.getId() == null) arg.setId(1L);
            return arg;
        });
        when(orderRepository.findById(1L)).thenReturn(Optional.of(o));

        Order saved = orderService.createOrder(o);
        assertEquals(100.0, saved.getTotalAmount());

        orderService.applyDiscount(1L, 10.0);
        assertEquals(90.0, o.getTotalAmount());

        orderService.restoreLastState(o);
        // restore should revert to previous snapshot (after creation), so total back to 100
        assertEquals(100.0, o.getTotalAmount());
    }
}
