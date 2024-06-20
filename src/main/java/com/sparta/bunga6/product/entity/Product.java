package com.sparta.bunga6.product.entity;

import com.sparta.bunga6.base.entity.Timestamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Product extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String name; // 상품이름

    @Column(nullable = false)
    private int price; // 가격

    @Column(nullable = false)
    private int stockQuantity; // 재고수량

}

