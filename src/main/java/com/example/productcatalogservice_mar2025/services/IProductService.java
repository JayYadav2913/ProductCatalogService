package com.example.productcatalogservice_mar2025.services;

import com.example.productcatalogservice_mar2025.models.Product;

import java.util.List;

public interface IProductService {

    public List<Product> getAllProducts();

    public Product getProductById(Long id);

    public Product addProduct(Product product);

}
