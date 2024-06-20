package com.sparta.bunga6.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.bunga6.jwt.JwtProvider;
import com.sparta.bunga6.user.dto.OrderCreateRequest;
import com.sparta.bunga6.user.dto.OrderResponse;
import com.sparta.bunga6.user.entity.Delivery;
import com.sparta.bunga6.user.entity.Order;
import com.sparta.bunga6.user.entity.OrderLine;
import com.sparta.bunga6.user.entity.Product;
import com.sparta.bunga6.user.entity.User;
import com.sparta.bunga6.user.repository.DeliveryRepository;
import com.sparta.bunga6.user.repository.OrderLineRepository;
import com.sparta.bunga6.user.repository.OrderRepository;
import com.sparta.bunga6.user.repository.ProductRepository;
import com.sparta.bunga6.user.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

	private final OrderRepository orderRepository;
	private final UserRepository userRepository;
	private final JwtProvider jwtProvider;
	private final ProductRepository productRepository;
	private final DeliveryRepository deliveryRepository;
	private final OrderLineRepository orderLineRepository;
	private Long count;

	@Transactional
	public OrderResponse createOrder(OrderCreateRequest orderRequest, HttpServletRequest request) {
		// 토큰에서 유저 정보 추출
		String token = jwtProvider.getAccessTokenFromHeader(request);
		String username = jwtProvider.getUsernameFromToken(token);
		User user = userRepository.findByUsername(username)
			.orElseThrow(() -> new IllegalArgumentException("일치하는 유저가 없습니다."));

		// Order 생성
		Order order = new Order(orderRequest, user);
		order.updateStatus("ORDERED");

		// Delivery 생성
		Delivery delivery = new Delivery(user);
		delivery.updateStatus("PROCESSED");
		order.setDelivery(delivery);

		// ID로 Product 검색
		Product product = productRepository.findById(orderRequest.getProductId())
			.orElseThrow(() -> new IllegalArgumentException("일치하는 상품이 없습니다."));

		// OrderLine 생성
		OrderLine orderLine = new OrderLine(orderRequest, order, product);
		orderLine.updateStatus("ORDERED");
		// 전체 가격 합산
		order.setTotalPrice(order.getOrderLineList().stream()
			.mapToInt(OrderLine::getOrderPrice)
			.sum());

		// Order에 OrderLine 추가
		order.addOrderLine(orderLine);

		// Order 저장
		orderRepository.save(order);

		return new OrderResponse(order);
	}

	public OrderResponse getOrder(Long orderId) {
		Order order = orderRepository.findById(orderId)
			.orElseThrow(() -> new IllegalArgumentException("해당 주문을 찾을 수 없습니다."));
		return new OrderResponse(order);
	}
}
