package com.sparta.bunga6.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.bunga6.user.entity.OrderLine;

public interface OrderLineRepository extends JpaRepository<OrderLine, Long> {
}
