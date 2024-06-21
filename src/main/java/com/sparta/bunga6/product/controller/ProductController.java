package com.sparta.bunga6.product.controller;

import com.sparta.bunga6.product.dto.*;
import com.sparta.bunga6.product.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {

    ProductService productService;

    @PostMapping
    public RegisterResponseDto regiseterProduct(@RequestBody RegisterRequestDto requsetDto,
                                                                HttpServletResponse response,
                                                                HttpServletRequest request) {
        return productService.registerProduct(requsetDto, response, request);
    }

    @GetMapping
    public List<FindProductResponseDto> findAllProduct() {
        return productService.findAllProduct();
    }

    @GetMapping("/{id}")
    public FindProductResponseDto findProduct(@PathVariable Long id) {
        return productService.findProduct(id);
    }

    @GetMapping("/page/{page}")
    public List<FindProductResponseDto> findPagingProduct(@PathVariable Long page, @RequestBody PagingRequestDto requestDto) {
        return productService.findPagingProduct(page, requestDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable Long id,
                                                @RequestBody UpdateProductRequestDto requestDto,
                                                             HttpServletRequest request) {
        productService.updateProduct(id, requestDto, request);
        return ResponseEntity.ok("상품의 정보를 성공적으로 수정하였습니다!");
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id,
                                             HttpServletRequest request) {
        return productService.deleteProduct(id, request);
    }
}
