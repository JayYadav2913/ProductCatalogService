package com.example.productcatalogservice_mar2025.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ProductDto {
    private UUID id;
    private String title;
    private String description;
    private Double amount;
    private String imageUrl;
    private CategoryDto category;

    public UUID getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public Double getAmount() {
        return this.amount;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public CategoryDto getCategory() {
        return this.category;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setCategory(CategoryDto category) {
        this.category = category;
    }
}
