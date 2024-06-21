package com.sparta.bunga6.order.entity;

import com.sparta.bunga6.base.entity.Timestamped;
import com.sparta.bunga6.order.dto.AddressRequest;
import com.sparta.bunga6.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "deliveries")
public class Delivery extends Timestamped {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "delivery_id")
	private Long id;

	@Column(nullable = false)
	private String status;

	@Column(nullable = false)
	private String address;

	public Delivery(User user) {
		this.address = user.getAddress();
	}

	public void updateStatus(String status) {
		this.status = status;
	}

	public void updateAddress(AddressRequest request) {
		this.address = request.getAddress();
	}
}
