package com.example.productcatalogservice_mar2025.controllers;


import com.example.productcatalogservice_mar2025.dtos.CategoryDto;
import com.example.productcatalogservice_mar2025.dtos.ProductDto;
import com.example.productcatalogservice_mar2025.models.Category;
import com.example.productcatalogservice_mar2025.models.Product;
import com.example.productcatalogservice_mar2025.services.IProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private IProductService productService;

    @GetMapping
    public List<ProductDto> getProducts() {
        List<Product> products = productService.getAllProducts();
        List<ProductDto> productDtos = new ArrayList<>();
        if(products!=null && !products.isEmpty()) {
            for(Product product : products) {
                ProductDto productDto = from(product);
                productDtos.add(productDto);
            }
            return productDtos;
        }
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("id") UUID id) {

        Product product = productService.getProductById(id);

        // Convert entity → DTO
        return ResponseEntity.ok(from(product));
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        Product response = productService.createProduct(from(productDto));
        return new ResponseEntity<>(from(response), HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> replaceProduct(
            @PathVariable UUID id,
            @RequestBody ProductDto productDto) {

        Product product = productService.replaceProduct(id, from(productDto));
        return ResponseEntity.ok(from(product));
    }

    // ✅ UPDATE (PATCH)
    @PatchMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable("id") UUID id, @RequestBody ProductDto productDto) {
        Product product = productService.updateProduct(id, from(productDto));
        return ResponseEntity.ok(from(product));
    }


    // ✅ DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") UUID id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    private Product from(ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setTitle(productDto.getTitle());
        product.setAmount(productDto.getAmount());
        product.setImageUrl(productDto.getImageUrl());
        product.setDescription(productDto.getDescription());
        if(productDto.getCategory() != null) {
            Category category = new Category();
            category.setId(productDto.getCategory().getId());
            category.setName(productDto.getCategory().getName());
            product.setCategory(category);
        }
        return product;
    }


    private ProductDto from(Product product) {
        ProductDto productDto=new ProductDto();
        productDto.setId(product.getId());
        productDto.setTitle(product.getTitle());
        productDto.setDescription(product.getDescription());
        productDto.setAmount(product.getAmount());
        productDto.setImageUrl(product.getImageUrl());
        if(product.getCategory()!=null){
            CategoryDto categoryDto=new CategoryDto();
            categoryDto.setName(product.getCategory().getName());
            categoryDto.setId(product.getCategory().getId());
            categoryDto.setDescription(product.getCategory().getDescription());
            productDto.setCategory(categoryDto);
        }
        return productDto;
    }

//    @ExceptionHandler({RuntimeException.class})
//    public ResponseEntity<String> handleExceptions(Exception exception) {
//        return new ResponseEntity<>("kuch toh phata hai", HttpStatus.BAD_REQUEST);
//    }
}
