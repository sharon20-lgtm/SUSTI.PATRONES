package com.techsolution.gestion_app.domain.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class StockAlert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;
    private String productName;
    private int stock;
    private LocalDateTime createdAt = LocalDateTime.now();

    public Long getId() { return id; }
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
