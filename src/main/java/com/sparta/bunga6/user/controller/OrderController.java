package com.sparta.bunga6.user.controller;

import com.sparta.bunga6.base.dto.CommonResponse;
import com.sparta.bunga6.security.UserDetailsImpl;
import com.sparta.bunga6.user.dto.*;
import com.sparta.bunga6.user.entity.Order;
import com.sparta.bunga6.user.entity.User;
import com.sparta.bunga6.user.service.OrderService;
import com.sparta.bunga6.user.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

import static com.sparta.bunga6.util.ControllerUtil.getFieldErrorResponseEntity;
import static com.sparta.bunga6.util.ControllerUtil.getResponseEntity;

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
}