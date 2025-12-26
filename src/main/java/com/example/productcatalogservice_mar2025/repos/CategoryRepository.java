package com.example.productcatalogservice_mar2025.repos;

import com.example.productcatalogservice_mar2025.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
}