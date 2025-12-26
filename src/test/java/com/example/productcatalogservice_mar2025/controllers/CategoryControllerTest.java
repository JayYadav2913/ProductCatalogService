package com.example.productcatalogservice_mar2025.controllers;



import com.example.productcatalogservice_mar2025.dtos.CategoryDto;
import com.example.productcatalogservice_mar2025.models.Category;
import com.example.productcatalogservice_mar2025.services.ICategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoryController.class)
@Import(com.example.productcatalogservice_mar2025.exceptions.GlobalExceptionHandler.class)
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ICategoryService categoryService;

    @Autowired
    private ObjectMapper objectMapper;

    // =========================
    // GET /categories - 200
    // =========================
    @Test
    void getAllCategories_success() throws Exception {
        Category c1 = new Category();
        c1.setId(UUID.randomUUID());
        c1.setName("Electronics");

        Category c2 = new Category();
        c2.setId(UUID.randomUUID());
        c2.setName("Fashion");

        when(categoryService.getAllCategories()).thenReturn(List.of(c1, c2));

        mockMvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Electronics"));
    }

    // =========================
    // GET /categories - 204
    // =========================
    @Test
    void getAllCategories_noContent() throws Exception {
        when(categoryService.getAllCategories()).thenReturn(List.of());

        mockMvc.perform(get("/categories"))
                .andExpect(status().isNoContent());
    }

    // =========================
    // GET /categories/{id} - 200
    // =========================
    @Test
    void getCategoryById_success() throws Exception {
        UUID id = UUID.randomUUID();

        Category category = new Category();
        category.setId(id);
        category.setName("Electronics");

        when(categoryService.getCategoryById(id)).thenReturn(category);

        mockMvc.perform(get("/categories/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.name").value("Electronics"));
    }

    // =========================
    // GET /categories/{id} - 404
    // =========================
    @Test
    void getCategoryById_notFound() throws Exception {
        UUID id = UUID.randomUUID();

        when(categoryService.getCategoryById(id)).thenReturn(null);

        mockMvc.perform(get("/categories/{id}", id))
                .andExpect(status().isNotFound());
    }

    // =========================
    // POST /categories - 201
    // =========================
    @Test
    void createCategory_success() throws Exception {
        Category saved = new Category();
        saved.setId(UUID.randomUUID());
        saved.setName("Books");

        when(categoryService.createCategory(any(Category.class)))
                .thenReturn(saved);

        CategoryDto dto = new CategoryDto();
        dto.setName("Books");

        mockMvc.perform(post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Books"));
    }

    // =========================
    // PUT /categories/{id} - 200
    // =========================
    @Test
    void replaceCategory_success() throws Exception {
        UUID id = UUID.randomUUID();

        Category updated = new Category();
        updated.setId(id);
        updated.setName("Updated");

        when(categoryService.replaceCategory(eq(id), any(Category.class)))
                .thenReturn(updated);

        CategoryDto dto = new CategoryDto();
        dto.setName("Updated");

        mockMvc.perform(put("/categories/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated"));
    }

    // =========================
    // PUT /categories/{id} - 404
    // =========================
    @Test
    void replaceCategory_notFound() throws Exception {
        UUID id = UUID.randomUUID();

        when(categoryService.replaceCategory(eq(id), any(Category.class)))
                .thenReturn(null);

        CategoryDto dto = new CategoryDto();
        dto.setName("DoesNotExist");

        mockMvc.perform(put("/categories/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }

    // =========================
    // PATCH /categories/{id} - 200
    // =========================
    @Test
    void updateCategory_success() throws Exception {
        UUID id = UUID.randomUUID();

        Category updated = new Category();
        updated.setId(id);
        updated.setName("Partial");

        when(categoryService.updateCategory(eq(id), any(Category.class)))
                .thenReturn(updated);

        CategoryDto dto = new CategoryDto();
        dto.setName("Partial");

        mockMvc.perform(patch("/categories/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Partial"));
    }

    // =========================
    // PATCH /categories/{id} - 404
    // =========================
    @Test
    void updateCategory_notFound() throws Exception {
        UUID id = UUID.randomUUID();

        when(categoryService.updateCategory(eq(id), any(Category.class)))
                .thenReturn(null);

        CategoryDto dto = new CategoryDto();
        dto.setName("NotFound");

        mockMvc.perform(patch("/categories/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }

    // =========================
    // DELETE /categories/{id} - 204
    // =========================
    @Test
    void deleteCategory_success() throws Exception {
        UUID id = UUID.randomUUID();

        doNothing().when(categoryService).deleteCategory(id);

        mockMvc.perform(delete("/categories/{id}", id))
                .andExpect(status().isNoContent());
    }
}