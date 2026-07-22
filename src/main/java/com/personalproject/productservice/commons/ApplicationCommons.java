package com.personalproject.productservice.commons;

import com.personalproject.productservice.dtos.TokenValidationResponseDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ApplicationCommons {

    RestTemplate restTemplate;

    public ApplicationCommons(@Qualifier("getLoadBalancedRestTemplate") RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void validateToken(String token) {
        if (token.isEmpty() || token.isBlank()) {
            throw new RuntimeException("Token Invalid : Empty Token");
        }

        String url = "http://userService/api/user/validate/" + token;

        TokenValidationResponseDTO response = restTemplate.getForObject(url, TokenValidationResponseDTO.class);

        if (response == null || !response.isValid()) {
            throw new RuntimeException("Invalid Token: Token Expired");
        }
    }

}
