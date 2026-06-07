package com.personalproject.productservice.services;

import com.personalproject.productservice.dtos.FakeStoreProductDTO;
import com.personalproject.productservice.models.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FakeStoreProductService implements ProductService{

    RestTemplate restTemplate;

    public FakeStoreProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Product getProductById(Long Id) {
        FakeStoreProductDTO fakeStoreProductDTO =
                restTemplate.getForObject("https://fakestoreapi.com/products/" + Id,
                        FakeStoreProductDTO.class);

        return fakeStoreProductDTO.toProduct(fakeStoreProductDTO);
    }
}
