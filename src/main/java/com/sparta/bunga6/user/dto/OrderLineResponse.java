package com.sparta.bunga6.user.dto;

import com.sparta.bunga6.user.entity.OrderLine;

import lombok.Data;

@Data
public class OrderLineResponse {

	private Long orderLineId;
	private String status;
	private Long productId;
	private Long count;
	private int orderPrice;

	public OrderLineResponse(OrderLine orderLine) {
		this.orderLineId = orderLine.getId();
		this.status = orderLine.getStatus();
		this.productId = orderLine.getProduct().getId();
		this.count = orderLine.getCount();
		this.orderPrice = orderLine.getOrderPrice();
	}

}