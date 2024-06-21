package com.sparta.bunga6.order.controller;

import com.sparta.bunga6.base.dto.CommonResponse;
import com.sparta.bunga6.order.dto.OrderCreateRequest;
import com.sparta.bunga6.order.dto.OrderResponse;
import com.sparta.bunga6.order.service.OrderService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static com.sparta.bunga6.util.ControllerUtil.getFieldErrorResponseEntity;
import static com.sparta.bunga6.util.ControllerUtil.getResponseEntity;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class OrderController {

	private final OrderService orderService;

	/**
	 * 주문 생성
	 */
	@PostMapping("/orders")
	public ResponseEntity<CommonResponse<?>> createOrder(
		@Valid @RequestBody OrderCreateRequest requestDto,
		BindingResult bindingResult,
		HttpServletRequest request
	) {
		if (bindingResult.hasErrors()) {
			return getFieldErrorResponseEntity(bindingResult, "주문 실패");
		}
		OrderResponse response  = orderService.createOrder(requestDto,request);

		return getResponseEntity(response, "주문 성공");
	}

	/**
	 * 주문 단건 조회
	 */
	@GetMapping("/orders/{ordersId}")
	public ResponseEntity<CommonResponse<?>> getOrders(
		@PathVariable Long ordersId,
		HttpServletRequest request
	) {
		OrderResponse response = orderService.getOrder(ordersId, request);

		return getResponseEntity(response, "주문 조회 성공");
	}

	/**
	 * 주문 전체 조회
	 */
	@GetMapping("/orders")
	public ResponseEntity<CommonResponse<?>> getAllOrders(HttpServletRequest request) {
		List<OrderResponse> response = orderService.getAllOrders(request);

		return getResponseEntity(response, "주문 조회 성공");
	}
}