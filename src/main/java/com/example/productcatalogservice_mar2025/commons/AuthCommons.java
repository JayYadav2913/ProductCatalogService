package com.example.productcatalogservice_mar2025.commons;

import com.example.productcatalogservice_mar2025.dtos.UserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthCommons {

    private final RestTemplate restTemplate;

    // Injected from application.properties — not hardcoded
    @Value("${userservice.base-url:http://localhost:9000}")
    private String userServiceBaseUrl;

    public AuthCommons(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean validateToken(String tokenValue) {
        UserDto userDto = restTemplate.getForObject(
                userServiceBaseUrl + "/users/validate/" + tokenValue,
                UserDto.class
        );

        return userDto != null;
    }
}