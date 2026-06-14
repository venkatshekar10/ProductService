package com.personalproject.productservice.dtos;

import com.personalproject.productservice.models.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponseDTO {

    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private double price;
    private String category;

    public static ProductResponseDTO from(Product product) {
        ProductResponseDTO productResponseDTO = new ProductResponseDTO();
        productResponseDTO.setId(product.getId());
        productResponseDTO.setName(product.getName());
        productResponseDTO.setDescription(product.getDescription());
        productResponseDTO.setImageUrl(product.getImageUrl());
        productResponseDTO.setPrice(product.getPrice());
        productResponseDTO.setCategory(product.getCategory().getName());

        return productResponseDTO;
    }
}
