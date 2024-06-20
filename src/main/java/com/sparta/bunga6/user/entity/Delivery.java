package com.sparta.bunga6.user.entity;

import com.sparta.bunga6.base.entity.Timestamped;
import com.sparta.bunga6.user.dto.OrderCreateRequest;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}
