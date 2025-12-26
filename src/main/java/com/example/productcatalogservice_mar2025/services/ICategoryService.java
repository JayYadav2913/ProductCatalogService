package com.example.productcatalogservice_mar2025.services;

import com.example.productcatalogservice_mar2025.models.Category;

import java.util.List;
import java.util.UUID;

public interface ICategoryService {
    List<Category> getAllCategories();
    Category getCategoryById(UUID id);
    Category createCategory(Category category);
    Category replaceCategory(UUID id, Category category);
    Category updateCategory(UUID id, Category category);
    void deleteCategory(UUID id);
}