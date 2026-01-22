package com.example.productcatalogservice_mar2025.commons;

import com.example.productcatalogservice_mar2025.dtos.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
@Component
public class AuthCommons {

    private static RestTemplate restTemplate;

    public AuthCommons(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public static boolean validateToken(String tokenValue) {
        UserDto userDto = restTemplate.getForObject(
                "http://localhost:8080/users/validate/" + tokenValue,
                UserDto.class
        );

        return userDto != null;
    }
}
