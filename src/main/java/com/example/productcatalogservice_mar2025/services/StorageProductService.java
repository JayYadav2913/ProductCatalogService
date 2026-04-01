package com.example.productcatalogservice_mar2025.services;

import com.example.productcatalogservice_mar2025.exceptions.ResourceNotFoundException;
import com.example.productcatalogservice_mar2025.models.Category;
import com.example.productcatalogservice_mar2025.models.Product;
import com.example.productcatalogservice_mar2025.models.State;
import com.example.productcatalogservice_mar2025.repos.CategoryRepository;
import com.example.productcatalogservice_mar2025.repos.ProductRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service("storageProductService")
public class StorageProductService implements IProductService{

    private final ProductRepository productRepo;
    private final CategoryRepository categoryRepo;

    public StorageProductService(ProductRepository productRepo, CategoryRepository categoryRepo) {
        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;

    }

    @Override
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepo.findById(id)
                .orElseThrow(() ->
                new ResourceNotFoundException(
                        "Product not found with id: " + id
                ));
    }
    @Override
    public Product createProduct(Product product) {

        Category category = product.getCategory();

        if (category != null) {
            // Case 1: Existing category (has id)
            if (category.getId() != null) {
                category = categoryRepo.findById(category.getId())
                        .orElseThrow(() -> new RuntimeException("Category not found"));
                product.setCategory(category);
            }
            // Case 2: New category (no id) → persist it
            else {
                category = categoryRepo.save(category);
                product.setCategory(category);
            }
        }

        return productRepo.save(product);
    }



    @Override
    public Product replaceProduct(Long id, Product product) {
        // replace = full update (PUT)
        Product existing = productRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product not found with id: " + id
                        ));

        // Full replace (PUT semantics)
        product.setId(existing.getId());

        return productRepo.save(product);
    }


    @Override
    public Product updateProduct(Long id, Product product) {

        Product existingProduct = productRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product not found with id: " + id
                        ));

        // Partial update (PATCH semantics)
        if (product.getTitle() != null)
            existingProduct.setTitle(product.getTitle());

        if (product.getDescription() != null)
            existingProduct.setDescription(product.getDescription());

        if (product.getAmount() != null)
            existingProduct.setAmount(product.getAmount());

        if (product.getImageUrl() != null)
            existingProduct.setImageUrl(product.getImageUrl());

        if (product.getCategory() != null)
            existingProduct.setCategory(product.getCategory());

        return productRepo.save(existingProduct);
    }


    @Override
    public void deleteProduct(Long id) {
        Product product = productRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product not found with id: " + id
                        ));

        product.setState(State.DELETED);
        productRepo.save(product);
    }

    @Override
    public Page<Product> getProductByTitle(String title, int pageNumber, int pageSize) {

        Sort sort=Sort.by(Sort.Direction.DESC, "amount");

        PageRequest pageRequest=PageRequest.of(pageNumber, pageSize,sort);

        return productRepo.findByTitleContainsIgnoreCase(title,pageRequest);
    }

}
