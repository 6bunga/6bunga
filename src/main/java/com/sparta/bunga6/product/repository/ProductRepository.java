package com.sparta.bunga6.product.repository;

import com.sparta.bunga6.product.entity.Product;
import com.sparta.bunga6.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByProductName(String name);

}
