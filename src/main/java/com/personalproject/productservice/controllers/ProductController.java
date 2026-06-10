package com.personalproject.productservice.controllers;

import com.personalproject.productservice.dtos.CreateFakeStoreProductDTO;
import com.personalproject.productservice.dtos.ProductResponseDTO;
import com.personalproject.productservice.models.Product;
import com.personalproject.productservice.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/products")
public class ProductController {

    ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{Id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long Id) {
       Product product = productService.getProductById(Id);
       ProductResponseDTO productResponseDTO = ProductResponseDTO.from(product);
       return new ResponseEntity<>(productResponseDTO, HttpStatus.OK);
    }

    @GetMapping("")
    public List<ProductResponseDTO> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        List<ProductResponseDTO> productResponseDTOS = new ArrayList<>();
        for(Product product : products) {
            ProductResponseDTO productResponseDTO = ProductResponseDTO.from(product);
            productResponseDTOS.add(productResponseDTO);
        }
        return productResponseDTOS;
    }

    @PostMapping("/createProduct")
    public ProductResponseDTO createProduct(@RequestBody CreateFakeStoreProductDTO createFakeStoreProductDTO) {
        Product product = productService.createProduct(
                createFakeStoreProductDTO.getName(),
                createFakeStoreProductDTO.getDescription(),
                createFakeStoreProductDTO.getPrice(),
                createFakeStoreProductDTO.getImageUrl(),
                createFakeStoreProductDTO.getCategory());
        return ProductResponseDTO.from(product);
    }
}
