package com.example.productcatalogservice_mar2025.controllers;

import com.example.productcatalogservice_mar2025.dtos.CategoryDto;
import com.example.productcatalogservice_mar2025.models.Category;
import com.example.productcatalogservice_mar2025.services.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    // ✅ GET all categories
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        if (categories.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<CategoryDto> categoryDtos = new ArrayList<>();
        for (Category category : categories) {
            categoryDtos.add(from(category));
        }
        return new ResponseEntity<>(categoryDtos, HttpStatus.OK);
    }

    // ✅ GET category by ID
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long id) {
        Category category = categoryService.getCategoryById(id);
        if (category == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(from(category), HttpStatus.OK);
    }

    // ✅ CREATE category
    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) {
        Category saved = categoryService.createCategory(from(categoryDto));
        return new ResponseEntity<>(from(saved), HttpStatus.CREATED);
    }

    // ✅ REPLACE (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> replaceCategory(@PathVariable Long id, @RequestBody CategoryDto categoryDto) {
        Category replaced = categoryService.replaceCategory(id, from(categoryDto));
        if (replaced == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(from(replaced), HttpStatus.OK);
    }

    // ✅ UPDATE (PATCH)
    @PatchMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable Long id, @RequestBody CategoryDto categoryDto) {
        Category updated = categoryService.updateCategory(id, from(categoryDto));
        if (updated == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(from(updated), HttpStatus.OK);
    }

    // ✅ DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // 🔄 DTO ↔ Entity conversion
    private Category from(CategoryDto dto) {
        Category category = new Category();
        category.setId(dto.getId());
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        return category;
    }

    private CategoryDto from(Category category) {
        CategoryDto dto = new CategoryDto();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        return dto;
    }
}
