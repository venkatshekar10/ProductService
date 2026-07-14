package com.personalproject.productservice.commons;

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
        if(token.isEmpty() || token.isBlank()) {
            throw new RuntimeException("Token Invalid : Empty Token");
        }

        String url = "http://userService/api/user/validate/"+token;

        Boolean isValidToken = restTemplate.getForObject(url, Boolean.class);

        if(Boolean.FALSE.equals(isValidToken)) {
            throw new RuntimeException("Invalid Token: Token Expired");
        }
    }

}
