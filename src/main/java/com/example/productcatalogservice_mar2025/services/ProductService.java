package com.example.productcatalogservice_mar2025.services;

import com.example.productcatalogservice_mar2025.clients.FakeStoreApiClient;
import com.example.productcatalogservice_mar2025.dtos.FakeStoreProductDto;
import com.example.productcatalogservice_mar2025.models.Category;
import com.example.productcatalogservice_mar2025.models.Product;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService implements IProductService {
    @Autowired
    private FakeStoreApiClient fakeStoreApiClient;

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        FakeStoreProductDto[] fakeStoreProductDtos = fakeStoreApiClient.getAllProducts();
        for (FakeStoreProductDto fakeStoreProductDto : fakeStoreProductDtos) {
            products.add(from(fakeStoreProductDto));
        }
        return products;
    }

    @Override
    public Product getProductById(UUID id) {
        return null;
    }

    public Product getProductById(Long id) {
        FakeStoreProductDto fakeStoreProductDto = fakeStoreApiClient.getProductById(id);
        if (fakeStoreProductDto != null) {
            return from(fakeStoreProductDto);
        }
        return null;
    }

    public Product createProduct(Product product) {
        FakeStoreProductDto fakeStoreProductDto = from(product);
        FakeStoreProductDto response = fakeStoreApiClient.addProduct(fakeStoreProductDto);
        return from(response);
    }

    @Override
    public Product replaceProduct(UUID id, Product product) {
        return null;
    }

    @Override
    public Product updateProduct(UUID id, Product product) {
        return null;
    }

//    public Product replaceProduct(Long id, Product product) {
//        FakeStoreProductDto fakeStoreProductDto = from(product);
//        FakeStoreProductDto response = fakeStoreApiClient.replaceProduct(id, fakeStoreProductDto);
//        return from(response);
//    }

//    public Product updateProduct(Long id, Product product) {
//        FakeStoreProductDto fakeStoreProductDto = from(product);
//        FakeStoreProductDto response = fakeStoreApiClient.updateProduct(id, fakeStoreProductDto);
//        return from(response);
//    }

    public void deleteProduct(UUID id) {
//        fakeStoreApiClient.deleteProduct(id);
    }

    // --- Mapping methods ---

    private Product from(FakeStoreProductDto fakeStoreProductDto) {
        Product product = new Product();
       // product.setId(fakeStoreProductDto.getId());
        product.setTitle(fakeStoreProductDto.getTitle());
        product.setDescription(fakeStoreProductDto.getDescription());
        product.setAmount(fakeStoreProductDto.getPrice());
        product.setImageUrl(fakeStoreProductDto.getImage());
        Category category = new Category();
        category.setName(fakeStoreProductDto.getCategory());
        product.setCategory(category);
        return product;
    }

    private FakeStoreProductDto from(Product product) {
        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
       // fakeStoreProductDto.setId(product.getId());
        fakeStoreProductDto.setTitle(product.getTitle());
        fakeStoreProductDto.setPrice(product.getAmount());
        fakeStoreProductDto.setDescription(product.getDescription());
        fakeStoreProductDto.setImage(product.getImageUrl());
        if (product.getCategory() != null) {
            fakeStoreProductDto.setCategory(product.getCategory().getName());
        }
        return fakeStoreProductDto;
    }
}