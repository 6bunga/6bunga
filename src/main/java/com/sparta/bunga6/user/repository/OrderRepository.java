package com.sparta.bunga6.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.bunga6.user.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
