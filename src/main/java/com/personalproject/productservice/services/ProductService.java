package com.personalproject.productservice.services;

import com.personalproject.productservice.dtos.CreateFakeStoreProductDTO;
import com.personalproject.productservice.models.Product;

import java.util.List;

public interface ProductService {

    Product getProductById(Long Id);
    List<Product> getAllProducts();
    Product createProduct(String name, String description, double price,
                          String imageUrl, String category);
}
