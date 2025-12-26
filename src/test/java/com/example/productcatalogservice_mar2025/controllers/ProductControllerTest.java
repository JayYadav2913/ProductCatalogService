package com.example.productcatalogservice_mar2025.controllers;

import com.example.productcatalogservice_mar2025.dtos.ProductDto;
import com.example.productcatalogservice_mar2025.exceptions.GlobalExceptionHandler;
import com.example.productcatalogservice_mar2025.exceptions.ResourceNotFoundException;
import com.example.productcatalogservice_mar2025.models.Product;
import com.example.productcatalogservice_mar2025.services.IProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
@Import(com.example.productcatalogservice_mar2025.exceptions.GlobalExceptionHandler.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    // =========================
    // GET /products/{id} - 200
    // =========================
    @Test
    void getProductById_success() throws Exception {
        UUID id = UUID.randomUUID();

        Product product = new Product();
        product.setId(id);
        product.setTitle("iPhone 16");

        when(productService.getProductById(id)).thenReturn(product);

        mockMvc.perform(get("/products/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.title").value("iPhone 16"));
    }

    // =========================
    // GET /products/{id} - 404
    // =========================
    @Test
    void getProductById_notFound() throws Exception {
        UUID id = UUID.randomUUID();

        when(productService.getProductById(id))
                .thenThrow(new ResourceNotFoundException("Product not found"));

        mockMvc.perform(get("/products/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Product not found"));
    }

    // =========================
    // GET /products - 200
    // =========================
    @Test
    void getAllProducts_success() throws Exception {
        Product p1 = new Product();
        p1.setId(UUID.randomUUID());
        p1.setTitle("iPhone");

        Product p2 = new Product();
        p2.setId(UUID.randomUUID());
        p2.setTitle("Samsung");

        when(productService.getAllProducts()).thenReturn(List.of(p1, p2));

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    // =========================
    // POST /products - 201
    // =========================
    @Test
    void createProduct_success() throws Exception {
        Product product = new Product();
        product.setId(UUID.randomUUID());
        product.setTitle("MacBook");

        when(productService.createProduct(any(Product.class)))
                .thenReturn(product);

        ProductDto dto = new ProductDto();
        dto.setTitle("MacBook");

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("MacBook"));
    }

    // =========================
    // PUT /products/{id} - 200
    // =========================
    @Test
    void replaceProduct_success() throws Exception {
        UUID id = UUID.randomUUID();

        Product product = new Product();
        product.setId(id);
        product.setTitle("Updated");

        when(productService.replaceProduct(eq(id), any(Product.class)))
                .thenReturn(product);

        ProductDto dto = new ProductDto();
        dto.setTitle("Updated");

        mockMvc.perform(put("/products/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated"));
    }

    // =========================
    // PATCH /products/{id} - 200
    // =========================
    @Test
    void updateProduct_success() throws Exception {
        UUID id = UUID.randomUUID();

        Product product = new Product();
        product.setId(id);
        product.setTitle("Partial Update");

        when(productService.updateProduct(eq(id), any(Product.class)))
                .thenReturn(product);

        ProductDto dto = new ProductDto();
        dto.setTitle("Partial Update");

        mockMvc.perform(patch("/products/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Partial Update"));
    }

    // =========================
    // DELETE /products/{id} - 204
    // =========================
    @Test
    void deleteProduct_success() throws Exception {
        UUID id = UUID.randomUUID();

        doNothing().when(productService).deleteProduct(id);

        mockMvc.perform(delete("/products/{id}", id))
                .andExpect(status().isNoContent());
    }
}
