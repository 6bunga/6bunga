package com.sparta.bunga6.user.dto;

import lombok.Data;

@Data
public class OrderCreateRequest {
	private Long productId;
	private Long count;
}
