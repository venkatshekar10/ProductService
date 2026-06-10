package com.personalproject.productservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateFakeStoreProductDTO {
    private String name;
    private String description;
    private String imageUrl;
    private double price;
    private String category;
}
