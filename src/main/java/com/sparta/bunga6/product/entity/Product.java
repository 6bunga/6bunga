package com.sparta.bunga6.product.entity;

import com.sparta.bunga6.base.entity.Timestamped;
import jakarta.persistence.*;

@Entity

public class Product extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;
}
