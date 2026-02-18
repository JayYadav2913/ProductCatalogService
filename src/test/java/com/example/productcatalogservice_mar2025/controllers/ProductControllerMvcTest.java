package com.example.productcatalogservice_mar2025.controllers;


import com.example.productcatalogservice_mar2025.dtos.ProductDto;
import com.example.productcatalogservice_mar2025.models.Product;
import com.example.productcatalogservice_mar2025.services.IProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
public class ProductControllerMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IProductService productService;
    @Autowired
    private ObjectMapper objectMapper;

//    @Autowired
//    private ObjectMapper objectMapper;

    @Test
    public void TestGetAllProducts_RunSuccessfully() throws Exception {
        Product product = new Product();
        product.setId(1L);
        product.setTitle("MacBook Pro");

        Product product2 = new Product();
        product2.setId(1L);
        product2.setTitle("Iphone");

        List<Product> productList = new ArrayList<>();
        productList.add(product);
        productList.add(product2);

        when(productService.getAllProducts()).thenReturn(productList);


//        mockMvc.perform(get("/products"))
//                .andExpect(status().isOk())
//                .andExpect(content().string(objectMapper.writeValueAsString(productList)));

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value("MacBook Pro"))
                .andExpect(jsonPath("$[1].title").value("Iphone"));
    }

    @Test
    public void Test_CreateProduct_RunSuccessfully() throws Exception {
        //Arrange
        Product product = new Product();
        Long productId = 1L;
         product.setId(productId);
         product.setTitle("Notebook");

        ProductDto productDto =new ProductDto();
        productDto.setId(productId);
        productDto.setTitle("Notebook");

         when(productService.createProduct(any(Product.class))).thenReturn(product);

         //Act
        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(productId.toString()))
                .andExpect(jsonPath("$.title").value("Notebook"));


        //Assert
    }
}
