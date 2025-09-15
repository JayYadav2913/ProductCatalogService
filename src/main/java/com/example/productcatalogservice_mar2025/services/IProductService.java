package com.example.productcatalogservice_mar2025.services;

import com.example.productcatalogservice_mar2025.models.Product;

import java.util.List;

public interface IProductService {

    List<Product> getAllProducts();

    Product getProductById(Long id);

    Product addProduct(Product product);

    Product replaceProduct(Long id, Product product);

    Product updateProduct(Long id, Product product);

    void deleteProduct(Long id);
}

