package com.personalproject.productservice.services;

import com.personalproject.productservice.exceptions.CategoryNotFoundException;
import com.personalproject.productservice.exceptions.ProductNotFoundException;
import com.personalproject.productservice.models.Category;
import com.personalproject.productservice.models.Product;
import com.personalproject.productservice.models.ProductDocument;
import com.personalproject.productservice.repositories.CategoryRepository;
import com.personalproject.productservice.repositories.ProductRepository;
import com.personalproject.productservice.repositories.ProductSearchRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service("ProductDBService")
public class ProductDBService implements ProductService {

    ProductRepository productRepository;
    CategoryRepository categoryRepository;
    ProductSearchRepository productSearchRepository;

    public ProductDBService(ProductRepository productRepository,
                            CategoryRepository categoryRepository,
                            ProductSearchRepository productSearchRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productSearchRepository = productSearchRepository;
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
        Product newProduct = productRepository.save(product);

        //Index into ElasticSearch
        ProductDocument doc = new ProductDocument();
        doc.setId(String.valueOf(newProduct.getId()));
        doc.setName(newProduct.getName());
        doc.setDescription(newProduct.getDescription());
        doc.setPrice(newProduct.getPrice());
        doc.setCategory(newProduct.getCategory().getName());
        productSearchRepository.save(doc);
        //Index into ElasticSearch

        return newProduct;
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
        Product updatedProduct = productRepository.save(product);

        //Index into ElasticSearch
        ProductDocument doc = new ProductDocument();
        doc.setId(String.valueOf(updatedProduct.getId()));
        doc.setName(updatedProduct.getName());
        doc.setDescription(updatedProduct.getDescription());
        doc.setPrice(updatedProduct.getPrice());
        doc.setCategory(updatedProduct.getCategory().getName());
        productSearchRepository.save(doc);
        //Index into ElasticSearch

        return updatedProduct;
    }

    @Override
    public List<Product> getAllProductsFromCategory(String name) {
//        Optional<Category> optionalCategory = categoryRepository.findByName(name);
//        if(optionalCategory.isEmpty()) {
//            throw new CategoryNotFoundException("Category " +name+ " not found");
//        }
//        Category category = optionalCategory.get();
//        QueryByMethod
//        List<Product> products = productRepository.findByCategory(category);
//        DerivedQuery
//        List<Product> products = productRepository.findByCategory_Name(name);
//        HQL
//        List<Product> products = productRepository.findByCategoryName(name);
//        Native Query
        return productRepository.findByCategoryNameNative(name);
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
