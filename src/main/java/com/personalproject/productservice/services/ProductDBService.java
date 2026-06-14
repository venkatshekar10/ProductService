package com.personalproject.productservice.services;

import com.personalproject.productservice.exceptions.ProductNotFoundException;
import com.personalproject.productservice.models.Category;
import com.personalproject.productservice.models.Product;
import com.personalproject.productservice.repositories.CategoryRepository;
import com.personalproject.productservice.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static ch.qos.logback.core.joran.spi.ConsoleTarget.findByName;

@Service("ProductDBService")
public class ProductDBService implements ProductService {

    ProductRepository productRepository;
    CategoryRepository categoryRepository;

    public ProductDBService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product getProductById(Long Id) {
        Optional<Product> optionalProduct = productRepository.findById(Id);
        if(optionalProduct.isEmpty()) {
            throw new ProductNotFoundException("Product not found with id : "+Id);
        }
        return optionalProduct.get();
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product createProduct(String name, String description, double price, String imageUrl, String category) {

        Category categoryObject = validateCategoryExist(category);

        Product product = new Product();
        product.setCategory(categoryObject);
        product.setPrice(price);
        product.setDescription(description);
        product.setImageUrl(imageUrl);
        product.setName(name);
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Long Id, String name, String description, double price, String imageUrl) {
        Optional<Product> optionalProduct = productRepository.findById(Id);
        if(optionalProduct.isEmpty()) {
            throw new ProductNotFoundException("Product not found with id : "+Id);
        }
        Product product = optionalProduct.get();
        if(!Objects.equals(name, product.getName()) && name != null) {
            product.setName(name);
        }
        if(!Objects.equals(description, product.getDescription()) && description != null) {
            product.setDescription(description);
        }
        if(!(product.getPrice() == price) && price != 0 ) {
            product.setPrice(price);
        }
        if(!Objects.equals(imageUrl, product.getImageUrl()) && imageUrl != null) {
            product.setImageUrl(imageUrl);
        }
        return productRepository.save(product);
    }

    public Category validateCategoryExist(String name) {
        Optional<Category> optionalCategory = categoryRepository.findByName(name);
        if(optionalCategory.isPresent()) {
            return optionalCategory.get();
        }

        Category category = new Category();
        category.setName(name);
        return categoryRepository.save(category);
    }
}
