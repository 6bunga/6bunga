package com.sparta.bunga6.user.entity;

import com.sparta.bunga6.base.entity.Timestamped;
import com.sparta.bunga6.user.dto.OrderCreateRequest;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "order_lines")
public class OrderLine extends Timestamped {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_line_id")
	private Long id;

	@Setter
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id", nullable = false)
	private Order order;

	@Column(nullable = false)
	private String status;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;

	@Column(nullable = false)
	private Long count;

	@Column
	private int orderPrice;

	public OrderLine(OrderCreateRequest request, Order order, Product product) {
		this.count = request.getCount();
		this.order = order;
		this.product = product;
	}

	public void updateStatus(String status) {
		this.status = status;
	}

	public void orderPrice(int price) {
		this.orderPrice = price;
	}

}
