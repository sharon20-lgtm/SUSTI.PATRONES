package com.techsolution.gestion_app.features.order.command;

import com.techsolution.gestion_app.service.OrderService;

public class ApplyDiscountCommand implements Command {

    private final OrderService orderService;
    private final Long orderId;
    private final double porcentaje;

    public ApplyDiscountCommand(OrderService orderService, Long orderId, double porcentaje) {
        this.orderService = orderService;
        this.orderId = orderId;
        this.porcentaje = porcentaje;
    }

    @Override
    public void execute() {
        orderService.applyDiscount(orderId, porcentaje);
    }
}
