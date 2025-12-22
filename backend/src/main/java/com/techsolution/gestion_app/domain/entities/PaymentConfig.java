package com.techsolution.gestion_app.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class PaymentConfig {

    @Id
    private final Long id = 1L; // solo una config global

    private boolean paypalActivo = true;
    private boolean yapeActivo = true;
    private boolean plinActivo = true;

    public Long getId() { return id; }
    public boolean isPaypalActivo() { return paypalActivo; }
    public void setPaypalActivo(boolean paypalActivo) { this.paypalActivo = paypalActivo; }
    public boolean isYapeActivo() { return yapeActivo; }
    public void setYapeActivo(boolean yapeActivo) { this.yapeActivo = yapeActivo; }
    public boolean isPlinActivo() { return plinActivo; }
    public void setPlinActivo(boolean plinActivo) { this.plinActivo = plinActivo; }
}
