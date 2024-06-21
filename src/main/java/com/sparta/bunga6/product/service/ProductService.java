package com.sparta.bunga6.product.service;

import com.sparta.bunga6.jwt.JwtProvider;
import com.sparta.bunga6.product.dto.*;
import com.sparta.bunga6.product.entity.Product;
import com.sparta.bunga6.product.repository.ProductRepository;
import com.sparta.bunga6.user.entity.User;
import com.sparta.bunga6.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ProductService {

   private final ProductRepository productRepository;
   private final JwtProvider jwtProvider;
   private final UserRepository userRepository;

    //상품등록
    @Transactional
    public RegisterResponseDto registerProduct(RegisterRequestDto requsetDto,
                                               HttpServletResponse response,
                                               HttpServletRequest request) {

        String token = jwtProvider.getAccessTokenFromHeader(request);
        String username = jwtProvider.getUsernameFromToken(token);
        //
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new IllegalArgumentException("등록된 사용자를 찾을수 없습니다.")
        );
        Product product = new Product(requsetDto, user);
        product.setUser(user);
        Product createProduct = productRepository.save(product);
        return new RegisterResponseDto(createProduct);
    }
    //상품 전체조회
    public List<FindProductResponseDto> findAllProduct() {
        List<Product> productList = productRepository.findAll();
        return productList.stream()
                .map(FindProductResponseDto::new)
                .collect(Collectors.toList());
    }

    //상품 페이징조회
    public List<FindProductResponseDto> findPagingProduct(Long page, PagingRequestDto requestDto) {
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
                .map(FindProductResponseDto::new)
                .toList();
    }

    //상품 단건조회
    public FindProductResponseDto findProduct(Long id) {
        Product product = productRepository.findById(id).
                orElseThrow(() ->
                        new IllegalArgumentException("입력하신 상품 ID가 존재하지 않습니다.")
                );
        return new FindProductResponseDto(product);
    }

    //상품 정보 전체 업데이트
    @Transactional
    public UpdateProductResponseDto updateProduct(Long id,
                                                  UpdateProductRequestDto requestDto,
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
        new UpdateProductResponseDto(updateProduct);
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
