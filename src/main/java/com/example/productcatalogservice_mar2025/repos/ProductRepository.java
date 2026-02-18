package com.example.productcatalogservice_mar2025.repos;

import com.example.productcatalogservice_mar2025.models.Category;
import com.example.productcatalogservice_mar2025.models.Product;
import com.example.productcatalogservice_mar2025.projections.ProductWithTitleAndPrice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByTitle(String title);

    Page<Product> findByTitleContainsIgnoreCase(String str, Pageable pageable);

    List<Product> findByCreatedAtBetween(Date start, Date end);
}
