package com.example.productcatalogservice_mar2025.repos;

import com.example.productcatalogservice_mar2025.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

}
