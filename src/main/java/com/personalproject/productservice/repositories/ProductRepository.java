package com.personalproject.productservice.repositories;

import com.personalproject.productservice.models.Category;
import com.personalproject.productservice.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

   Optional<Product> findById(Long Id);
   List<Product> findAll();
   Product save(Product product);

   List<Product> findByCategory(Category category);

   List<Product> findByCategory_Name(String categoryName);

   @Query("select p from Product p where p.category.name = :categoryName " )
   List<Product> findByCategoryName(String categoryName);

   @Query(value = "select * from product p where category_id in (select c.id from category c where c.name = :categoryName)", nativeQuery = true)
   List<Product> findByCategoryNameNative(@Param("categoryName") String categoryName);
}