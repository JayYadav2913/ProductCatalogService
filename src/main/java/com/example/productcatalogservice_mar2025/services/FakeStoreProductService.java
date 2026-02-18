package com.example.productcatalogservice_mar2025.services;

import com.example.productcatalogservice_mar2025.clients.FakeStoreApiClient;
import com.example.productcatalogservice_mar2025.dtos.FakeStoreProductDto;
import com.example.productcatalogservice_mar2025.models.Category;
import com.example.productcatalogservice_mar2025.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Primary
public class FakeStoreProductService implements IProductService {
    @Autowired
    private FakeStoreApiClient fakeStoreApiClient;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public FakeStoreProductService(FakeStoreApiClient fakeStoreApiClient, RedisTemplate<String, Object> redisTemplate) {
        this.fakeStoreApiClient = fakeStoreApiClient;
        this.redisTemplate = redisTemplate;
    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        FakeStoreProductDto[] fakeStoreProductDtos = fakeStoreApiClient.getAllProducts();
        for (FakeStoreProductDto fakeStoreProductDto : fakeStoreProductDtos) {
            products.add(from(fakeStoreProductDto));
        }
        return products;
    }


    public Product getProductById(Long id) {

        //First check if the product with the Id is present in the cache or not.
        Product product= (Product) redisTemplate.opsForHash().get("PRODUCTS","PRODUCT_"+id);

        if (product != null){
            //Cache Hit
            return product;
        }

        //Cache Miss
        FakeStoreProductDto fakeStoreProductDto = fakeStoreApiClient.getProductById(id);
        if (fakeStoreProductDto != null) {

            //Before returning the product, store it in redis.
            redisTemplate.opsForHash().put("PRODUCTS","PRODUCT_"+id, from(fakeStoreProductDto));

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
    public Product replaceProduct(Long id, Product product) {
        return null;
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        return null;
    }

    @Override
    public void deleteProduct(Long id) {

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

    @Override
    public Page<Product> getProductByTitle(String title, int pageNumber, int pageSize) {
        return null;
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