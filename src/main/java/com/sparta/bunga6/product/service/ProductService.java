package com.sparta.bunga6.product.service;

import com.sparta.bunga6.product.dto.FindProductResponseDto;
import com.sparta.bunga6.product.dto.RegisterRequsetDto;
import com.sparta.bunga6.product.dto.RegisterResponseDto;
import com.sparta.bunga6.product.entity.Product;
import com.sparta.bunga6.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    ProductRepository productRepository;

    public RegisterResponseDto registerProduct(RegisterRequsetDto request) {

        Product product = new Product();
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());
        Product createProduct = productRepository.save(product);
        RegisterResponseDto responseDto = new RegisterResponseDto(createProduct);
        return responseDto;
    }

    public List<FindProductResponseDto> findAllProduct() {
        List<Product> productList = productRepository.findAll();
        return productList.stream()
                .map(FindProductResponseDto::new)
                .collect(Collectors.toList());
    }
}
