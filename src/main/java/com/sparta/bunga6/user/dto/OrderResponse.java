package com.sparta.bunga6.user.dto;

import com.sparta.bunga6.user.entity.Delivery;
import com.sparta.bunga6.user.entity.Order;
import com.sparta.bunga6.user.entity.OrderLine;
import com.sparta.bunga6.user.entity.User;

import lombok.Data;
import lombok.Getter;

@Data
public class OrderResponse {

	private Long orderId;
	private Long userId;
	private Long OrderLineId;
	private Long deliveryId;
	private Long count;
	private String orderStatus;
	private String deliveryStatus;
	private String address;
	private int orderPrice;


	public OrderResponse(Order order, User user, Delivery delivery, OrderLine orderLine) {
		this.orderId = order.getId();
		this.userId = user.getId();
		this.OrderLineId = orderLine.getId();
		this.deliveryId = delivery.getId();
		this.count = orderLine.getCount();
		this.orderStatus = order.getStatus();
		this.deliveryStatus = delivery.getStatus();
		this.address = delivery.getAddress();
		this.orderPrice = orderLine.getOrderPrice();
	}
}
