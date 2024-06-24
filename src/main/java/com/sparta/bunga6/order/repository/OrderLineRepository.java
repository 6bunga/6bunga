package com.sparta.bunga6.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.bunga6.orderline.OrderLine;

public interface OrderLineRepository extends JpaRepository<OrderLine, Long> {
}
