package com.techsolution.gestion_app.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import com.techsolution.gestion_app.domain.entities.StockAlert;
import com.techsolution.gestion_app.service.StockAlertService;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/alerts")
@RequiredArgsConstructor
public class AlertController {

    private final StockAlertService stockAlertService;

    @GetMapping
    public List<StockAlert> getAll() {
        return stockAlertService.getAll();
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<StockAlert>> getByProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(stockAlertService.getByProduct(productId));
    }
}
