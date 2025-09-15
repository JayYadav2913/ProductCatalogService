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

@Slf4j
@RestController
public class ProductController {

    @Autowired
    private IProductService productService;

    @GetMapping("/products")
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

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("id") Long id) {
        try {
            if (id < 0) {
                throw new RuntimeException("Product not found");
            }
            else if(id == 0) {
                throw new RuntimeException("Invalid productId");
            }

            Product product = productService.getProductById(id);
            if (product == null) return null;
            return new ResponseEntity<>(from(product), HttpStatus.OK);
        }catch (RuntimeException exception) {
            //return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
            throw exception;
        }
    }

    @PostMapping("/products")
    public ProductDto createProduct(@RequestBody ProductDto product) {
        return null;
    }


    @PutMapping("/products/{id}")
    public ProductDto replaceProduct(@PathVariable("id") Long id, @RequestBody ProductDto productDto) {
        Product product = productService.replaceProduct(id,from(productDto));
        return from(product);
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
