package com.personalproject.productservice.services;

import com.personalproject.productservice.dtos.FakeStoreProductDTO;
import com.personalproject.productservice.dtos.FakeStoreProductRequestDTO;
import com.personalproject.productservice.exceptions.ProductNotFoundException;
import com.personalproject.productservice.models.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service("FakeStoreProductService")
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

        if(fakeStoreProductDTO == null) {
            throw new ProductNotFoundException("Product not found with id : " + Id);
        }

        return fakeStoreProductDTO.toProduct(fakeStoreProductDTO);
    }

    @Override
    public List<Product> getAllProducts() {

        List<Product> products = new ArrayList<>();

        FakeStoreProductDTO[] fakeStoreProductDTOs =
                restTemplate.getForObject("https://fakestoreapi.com/products",
                        FakeStoreProductDTO[].class);

        for(FakeStoreProductDTO fakeStoreProductDTO : fakeStoreProductDTOs) {
            products.add(fakeStoreProductDTO.toProduct(fakeStoreProductDTO));
        }

        return products;
    }

    @Override
    public Product createProduct(String name, String description, double price,
                                 String imageUrl, String category) {

        FakeStoreProductRequestDTO fakeStoreProductRequestDTO = new FakeStoreProductRequestDTO();
        fakeStoreProductRequestDTO.setTitle(name);
        fakeStoreProductRequestDTO.setDescription(description);
        fakeStoreProductRequestDTO.setPrice(price);
        fakeStoreProductRequestDTO.setImage(imageUrl);
        fakeStoreProductRequestDTO.setCategory(category);

        FakeStoreProductDTO fakeStoreProductDTO =
                restTemplate.postForObject("https://fakestoreapi.com/products",
                        fakeStoreProductRequestDTO,
                        FakeStoreProductDTO.class);

        return fakeStoreProductDTO.toProduct(fakeStoreProductDTO);
    }

    @Override
    public Product updateProduct(Long Id, String name, String description, double price, String imageUrl) {
        return null;
    }

    @Override
    public List<Product> getAllProductsFromCategory(String name) {
        return List.of();
    }
}
