package com.techsolution.gestion_app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.techsolution.gestion_app.domain.entities.Product;
import com.techsolution.gestion_app.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final StockObserverService stockObserverService;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Product saveProduct(Product product) {
        Product saved = productRepository.save(product);
        stockObserverService.checkStock(saved);
        return saved;
    }

    public Product updateProduct(Long id, Product data) {
        Product p = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Producto no existe"));
        p.setProducto(data.getProducto());
        p.setDescripcion(data.getDescripcion());
        p.setPrecio(data.getPrecio());
        p.setStock(data.getStock());
        p.setStockMinimo(data.getStockMinimo());
        Product saved = productRepository.save(p);
        stockObserverService.checkStock(saved);
        return saved;
    }

    public Product updateStock(Long id, int nuevoStock) {
        Product p = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Producto no existe"));
        p.setStock(nuevoStock);
        Product saved = productRepository.save(p);
        stockObserverService.checkStock(saved);
        return saved;
    }
}
