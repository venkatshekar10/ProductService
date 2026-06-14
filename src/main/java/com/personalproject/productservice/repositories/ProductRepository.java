package com.personalproject.productservice.repositories;

import com.personalproject.productservice.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

   Optional<Product> findById(Long Id);
   List<Product> findAll();
   Product save(Product product);

}
