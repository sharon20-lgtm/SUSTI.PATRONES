package com.techsolution.gestion_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.techsolution.gestion_app.domain.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
