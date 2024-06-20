package com.sparta.bunga6.user.controller;

import com.sparta.bunga6.base.dto.CommonResponse;
import com.sparta.bunga6.security.UserDetailsImpl;
import com.sparta.bunga6.user.dto.*;
import com.sparta.bunga6.user.entity.User;
import com.sparta.bunga6.user.service.UserService;
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

	private final UserService userService;

	/**
	 * 주문 생성
	 */
	@PostMapping("/orders")
	public ResponseEntity<CommonResponse<?>> signup(
		@Valid @RequestBody SignupRequest request,
		BindingResult bindingResult
	) {
		if (bindingResult.hasErrors()) {
			return getFieldErrorResponseEntity(bindingResult, "주문 실패");
		}
		User user = userService.signup(request);
		SignupResponse response = new SignupResponse(user);

		return getResponseEntity(response, "주문 성공");
	}
}