package com.nazarii.yukhnovskyi.ecommerceProject.repo;

import com.nazarii.yukhnovskyi.ecommerceProject.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
}
