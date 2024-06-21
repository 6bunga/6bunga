package com.sparta.bunga6.order.dto;

import lombok.Data;

@Data
public class OrderCreateRequest {
	private Long productId;
	private Long count;
}
