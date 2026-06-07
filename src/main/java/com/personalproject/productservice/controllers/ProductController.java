package com.personalproject.productservice.controllers;

import com.personalproject.productservice.dtos.ProductResponseDTO;
import com.personalproject.productservice.models.Product;
import com.personalproject.productservice.services.FakeStoreProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/products")
public class ProductController {

    FakeStoreProductService fakeStoreProductService;

    public ProductController(FakeStoreProductService fakeStoreProductService) {
        this.fakeStoreProductService = fakeStoreProductService;
    }

    @GetMapping("/{Id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long Id) {
       Product product = fakeStoreProductService.getProductById(Id);
       ProductResponseDTO productResponseDTO = ProductResponseDTO.from(product);
       return new ResponseEntity<ProductResponseDTO>(productResponseDTO, HttpStatus.OK);
    }
}
