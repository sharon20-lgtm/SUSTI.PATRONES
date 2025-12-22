package com.techsolution.gestion_app.controller.dto;

public class PaymentRequest {
    private double monto;
    private String metodo;

    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }
    public String getMetodo() { return metodo; }
    public void setMetodo(String metodo) { this.metodo = metodo; }
}
