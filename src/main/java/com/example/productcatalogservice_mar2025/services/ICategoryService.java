package com.example.productcatalogservice_mar2025.services;

import com.example.productcatalogservice_mar2025.models.Category;

import java.util.List;
import java.util.UUID;

public interface ICategoryService {
    List<Category> getAllCategories();
    Category getCategoryById(Long id);
    Category createCategory(Category category);
    Category replaceCategory(Long id, Category category);
    Category updateCategory(Long id, Category category);
    void deleteCategory(Long id);
}