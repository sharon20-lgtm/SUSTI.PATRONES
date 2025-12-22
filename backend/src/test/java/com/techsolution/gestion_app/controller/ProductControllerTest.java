package com.techsolution.gestion_app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techsolution.gestion_app.domain.entities.Product;
import com.techsolution.gestion_app.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ProductService productService;

    @Test
    void createProductReturnsOk() throws Exception {
        Product p = new Product();
        p.setProducto("Mouse");
        when(productService.saveProduct(any())).thenReturn(p);

        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(p)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.producto").value("Mouse"));
    }

    @Test
    void updateStockReturnsNotFoundIfMissing() throws Exception {
        when(productService.updateStock(Mockito.eq(1L), Mockito.eq(5))).thenThrow(new IllegalArgumentException("Producto no existe"));

        mockMvc.perform(put("/products/1/stock?stock=5"))
                .andExpect(status().isNotFound());
    }
}
