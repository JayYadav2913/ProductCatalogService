package com.example.productcatalogservice_mar2025.services;

import com.example.productcatalogservice_mar2025.models.Category;
import com.example.productcatalogservice_mar2025.repos.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Primary
public class StorageCategoryService implements ICategoryService {

    @Autowired
    private CategoryRepository categoryRepo;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepo.findAll();
    }

    @Override
    public Category getCategoryById(UUID id) {
        return categoryRepo.findById(id).orElse(null);
    }

    @Override
    public Category createCategory(Category category) {
        return categoryRepo.save(category);
    }



    @Override
    public Category replaceCategory(UUID id, Category category) {
        return categoryRepo.findById(id)
                .map(existing -> {
                    category.setId(id);
                    return categoryRepo.save(category);
                }).orElse(null);
    }

    @Override
    public Category updateCategory(UUID id, Category category) {
        return categoryRepo.findById(id)
                .map(existing -> {
                    if (category.getName() != null) existing.setName(category.getName());
                    if (category.getDescription() != null) existing.setDescription(category.getDescription());
                    if (category.getProducts() != null) existing.setProducts(category.getProducts());
                    return categoryRepo.save(existing);
                }).orElse(null);
    }

    @Override
    public void deleteCategory(UUID id) {
        if (categoryRepo.existsById(id)) {
            categoryRepo.deleteById(id);
        }
    }
}