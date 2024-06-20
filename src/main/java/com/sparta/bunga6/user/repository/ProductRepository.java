package com.sparta.bunga6.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.bunga6.user.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
