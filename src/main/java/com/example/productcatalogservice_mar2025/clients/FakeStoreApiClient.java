package com.example.productcatalogservice_mar2025.clients;

import com.example.productcatalogservice_mar2025.dtos.FakeStoreProductDto;
import com.example.productcatalogservice_mar2025.models.Product;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class FakeStoreApiClient {

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;


    public FakeStoreProductDto[] getAllProducts() {
        ResponseEntity<FakeStoreProductDto[]> response =
                requestForEntity("https://fakestoreapi.com/products", HttpMethod.GET,
                        null, FakeStoreProductDto[].class);

        if (response.getStatusCode().equals(HttpStatusCode.valueOf(200)) && response.getBody() != null) {
            return response.getBody();
        }
        return new FakeStoreProductDto[0];
    }

    public FakeStoreProductDto getProductById(Long id) {

        ResponseEntity<FakeStoreProductDto> fakeStoreProductDtoResponseEntity=
             requestForEntity("https://fakestoreapi.com/products/{id}",HttpMethod.GET,null,FakeStoreProductDto.class,id);

        FakeStoreProductDto fakeStoreProductDto=fakeStoreProductDtoResponseEntity.getBody();
        if(fakeStoreProductDtoResponseEntity.getStatusCode().equals(HttpStatusCode.valueOf(200))
                && fakeStoreProductDto!=null){
            return fakeStoreProductDto;
        }
        return null;
    }
    public FakeStoreProductDto addProduct(FakeStoreProductDto fakeStoreProductDto) {
        ResponseEntity<FakeStoreProductDto> response =
                requestForEntity("https://fakestoreapi.com/products", HttpMethod.POST,
                        fakeStoreProductDto, FakeStoreProductDto.class);

        return response.getBody();
    }

    public FakeStoreProductDto replaceProduct(Long id, FakeStoreProductDto fakeStoreProductDto) {
        ResponseEntity<FakeStoreProductDto> response =
                requestForEntity("https://fakestoreapi.com/products/{id}", HttpMethod.PUT,
                        fakeStoreProductDto, FakeStoreProductDto.class, id);

        return response.getBody();
    }

    public FakeStoreProductDto updateProduct(Long id, FakeStoreProductDto fakeStoreProductDto) {
        ResponseEntity<FakeStoreProductDto> response =
                requestForEntity("https://fakestoreapi.com/products/{id}", HttpMethod.PATCH,
                        fakeStoreProductDto, FakeStoreProductDto.class, id);

        return response.getBody();
    }

    public void deleteProduct(Long id) {
        requestForEntity("https://fakestoreapi.com/products/{id}", HttpMethod.DELETE,
                null, Void.class, id);
    }

    private <T> ResponseEntity<T> requestForEntity(String url,
                                                   HttpMethod httpMethod,
                                                   @Nullable Object request,
                                                   Class<T> responseType,
                                                   Object... uriVariables) throws RestClientException {
        RestTemplate restTemplate = restTemplateBuilder.build();
        RequestCallback requestCallback = restTemplate.httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return restTemplate.execute(url, httpMethod, requestCallback, responseExtractor, uriVariables);
    }
}
