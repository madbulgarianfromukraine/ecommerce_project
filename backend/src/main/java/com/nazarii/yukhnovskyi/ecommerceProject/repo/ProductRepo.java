package com.nazarii.yukhnovskyi.ecommerceProject.repo;

import com.nazarii.yukhnovskyi.ecommerceProject.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
    // JPQL - JPA Query Language
    @Query("SELECT p FROM Product p " +
            "WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(p.brand) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(p.category) LIKE LOWER(CONCAT('%', :keyword, '%'))" +
            "OR LOWER(p.desc) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Product> searchProducts(String keyword);
}
