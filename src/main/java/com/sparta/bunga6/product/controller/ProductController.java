package com.sparta.bunga6.product.controller;

import com.sparta.bunga6.base.dto.CommonResponse;
import com.sparta.bunga6.product.dto.*;
import com.sparta.bunga6.product.entity.Product;
import com.sparta.bunga6.product.service.ProductService;
import com.sparta.bunga6.security.UserDetailsImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.sparta.bunga6.util.ControllerUtil.getFieldErrorResponseEntity;
import static com.sparta.bunga6.util.ControllerUtil.getResponseEntity;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    ProductService productService;

    @PostMapping
    public ResponseEntity<CommonResponse<?>> regiseterProduct(
            @Valid @RequestBody RegisterRequest request,
            BindingResult bindingResult,
	    	@AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        if (bindingResult.hasErrors()) {
            return getFieldErrorResponseEntity(bindingResult, "상품 등록 실패!");

        }
        Product product = productService.registerProduct(request, userDetails.getUser());
        RegisterResponse response = new RegisterResponse(product);

        return getResponseEntity(response,"상품 등록 성공!");
    }


    @GetMapping
    public ResponseEntity<CommonResponse<?>> findAllProduct
    }

    @GetMapping("/{id}")
    public FindProductResponse findProduct(@PathVariable Long id) {
        return productService.findProduct(id);
    }

    @GetMapping("/page/{page}")
    public List<FindProductResponse> findPagingProduct(@PathVariable Long page, @RequestBody PagingRequest requestDto) {
        return productService.findPagingProduct(page, requestDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable Long id,
                                                @RequestBody UpdateProductRequest requestDto,
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
