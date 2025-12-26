package com.example.productcatalogservice_mar2025.services;

import com.example.productcatalogservice_mar2025.models.Product;

import java.util.List;
import java.util.UUID;

public interface IProductService {

    List<Product> getAllProducts();

    Product getProductById(UUID id);

    Product createProduct(Product product);

    Product replaceProduct(UUID id, Product product);

    Product updateProduct(UUID id, Product product);

    void deleteProduct(UUID id);
}

