package com.example.untitled34.service;

import com.example.untitled34.dto.ProductDto;
import com.example.untitled34.model.Product;
import com.example.untitled34.query.ProductQueryRepository;
import com.example.untitled34.query.ProductSearchCondition;
import com.example.untitled34.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductQueryRepository queryRepository;

    public Product create(ProductDto dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        return productRepository.save(product);
    }

    public List<Product> search(ProductSearchCondition condition) {     // dto 느낌으로 만든건데 search 용도라서 이렇게 지은듯
        return queryRepository.search(condition);                       // 이에 맞는 search 방법을 QueryDSL 기능으로 따로 만듬
    }

    public Product getById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new NoSuchElementException("제품을 찾을 수 없습니다. id=" + id));
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    public Product update(Long id, ProductDto dto) {
        Product product = getById(id);                                  // id 로 기존 product 를 꺼내온다
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        return productRepository.save(product);
    }


}