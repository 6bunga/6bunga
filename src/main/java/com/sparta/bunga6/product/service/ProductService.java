package com.sparta.bunga6.product.service;

import com.sparta.bunga6.jwt.RefreshTokenRepository;
import com.sparta.bunga6.product.dto.*;
import com.sparta.bunga6.product.entity.Product;
import com.sparta.bunga6.product.repository.ProductRepository;
import com.sparta.bunga6.user.entity.User;
import com.sparta.bunga6.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)

public class ProductService {

   private final ProductRepository productRepository;
   private final UserRepository userRepository;
   private final RefreshTokenRepository refreshTokenRepository;

    //상품등록
    @Transactional
    public Product registerProduct(RegisterRequest requset, User user) {
        Product product = new Product(requset, user);
        if (productRepository.existsByname(requset.getName())) {
            throw new IllegalArgumentException("이미 존재하는 상품입니다.");
        }
        return productRepository.save(product);
    }


    //상품 전체조회
    public List<FindProductResponse> findAllProduct() {
        List<Product> productList = productRepository.findAll();
        return productList.stream()
                .map(FindProductResponse::new)
                .collect(Collectors.toList());
    }

    //상품 페이징조회
    public List<FindProductResponse> findPagingProduct(Long page, PagingRequest requestDto) {
        Stream <Product> productStream = Stream.empty();

        // 정렬방식
        String sortBy = requestDto.getSortBy();

        if (sortBy == null) {
            productStream = productStream.sorted(Comparator.comparing(Product::getWriteDate).reversed());
        } else {
        }
        return productStream
                .skip((page - 1) * 5L)
                .limit(5)
                .map(FindProductResponse::new)
                .toList();
    }

    //상품 단건조회
    public FindProductResponse findProduct(Long id) {
        Product product = productRepository.findById(id).
                orElseThrow(() ->
                        new IllegalArgumentException("입력하신 상품 ID가 존재하지 않습니다.")
                );
        return new FindProductResponse(product);
    }

    //상품 정보 전체 업데이트
    @Transactional
    public UpdateProductResponse updateProduct(Long id,
                                               UpdateProductRequest requestDto,
                                               HttpServletRequest request) {
        // 토큰 검증
        String newBearerAccessToken = jwtProvider.getRefreshTokenFromRequest(request);
        String username = jwtProvider.getUsernameFromToken(newBearerAccessToken);

        // 상품 조회
        Product product = productRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("입력하신 상품 ID가 존재하지 않습니다"));
        // 본인 확인
        if (!product.getUser().getUsername().equals(username)) {
            throw new IllegalArgumentException("본인의 상품만 수정 할 수 있습니다.");
        }
        product.setName(requestDto.getName());
        product.setPrice(requestDto.getPrice());
        product.setStockQuantity(requestDto.getStockQuantity());

        Product updateProduct = productRepository.save(product);
        return
        new UpdateProductResponse(updateProduct);
    }

    // 상품삭제
    @Transactional
    public String deleteProduct(Long id,
                                HttpServletRequest request) {
        String newBearerAccessToken = jwtProvider.getRefreshTokenFromRequest(request);
        String username = jwtProvider.getUsernameFromToken(newBearerAccessToken);
        // 상품 조회
        Product product = productRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("입력하신 상품 ID가 존재하지 않습니다"));
        // 유저 조회
        if (!product.getUser().getUsername().equals(username)) {
            throw new IllegalArgumentException("본인의 상품만 수정 할 수 있습니다.");
        }
        // 삭제
        productRepository.deleteById(id);
        return "상품이 삭제되었습니다";
    }
}
