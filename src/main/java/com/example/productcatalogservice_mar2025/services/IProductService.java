package com.example.productcatalogservice_mar2025.services;

import com.example.productcatalogservice_mar2025.models.Product;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface IProductService {

    List<Product> getAllProducts();

    Product getProductById(Long id);

    Product createProduct(Product product);

    Product replaceProduct(Long id, Product product);

    Product updateProduct(Long id, Product product);

    void deleteProduct(Long id);


    Page<Product> getProductByTitle(String title, int pageNumber, int pageSize);
}

