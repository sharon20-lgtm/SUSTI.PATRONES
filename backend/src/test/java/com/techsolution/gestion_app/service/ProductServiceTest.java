package com.techsolution.gestion_app.service;

import com.techsolution.gestion_app.domain.entities.Product;
import com.techsolution.gestion_app.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ProductServiceTest {

    private ProductRepository productRepository;
    private StockObserverService stockObserverService;
    private ProductService productService;

    @BeforeEach
    void setup() {
        productRepository = Mockito.mock(ProductRepository.class);
        stockObserverService = Mockito.mock(StockObserverService.class);
        productService = new ProductService(productRepository, stockObserverService);
    }

    @Test
    void saveProductCallsObserver() {
        Product p = new Product();
        p.setProducto("A");
        when(productRepository.save(p)).thenReturn(p);

        Product saved = productService.saveProduct(p);
        assertEquals("A", saved.getProducto());
        verify(stockObserverService).checkStock(p);
    }

    @Test
    void updateStockUpdatesAndChecks() {
        Product p = new Product();
        p.setId(1L);
        p.setStock(10);
        when(productRepository.findById(1L)).thenReturn(Optional.of(p));
        when(productRepository.save(Mockito.any())).thenAnswer(i -> i.getArgument(0));

        Product updated = productService.updateStock(1L, 2);
        assertEquals(2, updated.getStock());
        verify(stockObserverService).checkStock(updated);
    }
}
