package com.example.productcatalogservice_mar2025.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class Category extends BaseModel {
    private String name;
   private String description;
   private List<Product> products;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
