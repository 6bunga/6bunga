package com.sparta.bunga6.product.controller;

import com.sparta.bunga6.base.dto.CommonResponse;
import com.sparta.bunga6.product.dto.FindProductResponseDto;
import com.sparta.bunga6.product.dto.RegisterRequsetDto;
import com.sparta.bunga6.product.dto.RegisterResponseDto;
import com.sparta.bunga6.product.entity.Product;
import com.sparta.bunga6.product.service.ProductService;
import com.sparta.bunga6.user.dto.SignupRequest;
import com.sparta.bunga6.user.dto.SignupResponse;
import com.sparta.bunga6.user.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.sparta.bunga6.util.ControllerUtil.getFieldErrorResponseEntity;
import static com.sparta.bunga6.util.ControllerUtil.getResponseEntity;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {
    ProductService productService;

    @PostMapping
    public ResponseEntity<String> regiseterProduct(@RequestBody @PathVariable RegisterRequsetDto request) {
        productService.registerProduct(request);
        return ResponseEntity.ok("상품등록을 완료했습니다!");
    }

    @GetMapping("/{productId}")
    public List<FindProductResponseDto> findAllProduct() {
        return productService.findAllProduct();
    }
}
