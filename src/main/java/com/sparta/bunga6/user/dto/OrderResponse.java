package com.sparta.bunga6.user.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.sparta.bunga6.user.entity.Delivery;
import com.sparta.bunga6.user.entity.Order;
import com.sparta.bunga6.user.entity.OrderLine;
import com.sparta.bunga6.user.entity.User;

import lombok.Data;

@Data
public class OrderResponse {

	private Long orderId;
	private Long userId;
	private Long deliveryId;
	private String orderStatus;
	private String deliveryStatus;
	private String address;
	private int orderPrice;
	private List<OrderLineResponse> orderLines;
	private int totalPrice;


	public OrderResponse(Order order, User user, Delivery delivery, OrderLine orderLine) {
		this.orderId = order.getId();
		this.userId = user.getId();
		this.deliveryId = delivery.getId();
		this.orderStatus = order.getStatus();
		this.deliveryStatus = delivery.getStatus();
		this.address = delivery.getAddress();
		this.orderPrice = orderLine.getOrderPrice();
	}

	public OrderResponse(Order order) {
		this.orderId = order.getId();
		this.userId = order.getUser().getId();
		this.orderLines = order.getOrderLineList().stream()
			.map(OrderLineResponse::new)
			.collect(Collectors.toList());
		this.deliveryId = order.getDelivery().getId();
		this.orderStatus = order.getStatus();
		this.deliveryStatus = order.getDelivery().getStatus();
		this.address = order.getDelivery().getAddress();
		this.totalPrice = order.getOrderLineList().stream()
			.mapToInt(OrderLine::getOrderPrice)
			.sum();
	}
}
