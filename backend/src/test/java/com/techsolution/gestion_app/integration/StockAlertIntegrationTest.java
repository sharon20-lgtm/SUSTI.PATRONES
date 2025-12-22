package com.techsolution.gestion_app.integration;

import com.techsolution.gestion_app.domain.entities.Product;
import com.techsolution.gestion_app.repository.StockAlertRepository;
import com.techsolution.gestion_app.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class StockAlertIntegrationTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StockAlertRepository stockAlertRepository;

    @Test
    void creatingProductBelowMinShouldCreateAlert() {
        Product p = new Product();
        p.setProducto("TestProd");
        p.setPrecio(10.0);
        p.setStock(2);
        p.setStockMinimo(5);

        productRepository.save(p);

        // after save the observer should have created an alert
        assertFalse(stockAlertRepository.findAll().isEmpty());
        assertEquals(1, stockAlertRepository.findAll().size());
        var a = stockAlertRepository.findAll().get(0);
        assertEquals("TestProd", a.getProductName());
        assertEquals(2, a.getStock());
    }
}
