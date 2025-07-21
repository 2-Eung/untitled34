package com.example.untitled34.controller;

import com.example.untitled34.dto.ProductDto;
import com.example.untitled34.model.Product;
import com.example.untitled34.query.ProductQueryRepository;
import com.example.untitled34.query.ProductSearchCondition;
import com.example.untitled34.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductQueryRepository queryRepository;

    @PostMapping
    public Product create(@Valid @RequestBody ProductDto dto) {
        return productService.create(dto);
    }

    @GetMapping
    public List<Product> search(ProductSearchCondition condition) {
        return productService.search(condition);
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable Long id) {
        return productService.getById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @PutMapping("/{id}")
    public Product update(@PathVariable Long id, @Valid @RequestBody ProductDto dto) {
        return productService.update(id, dto);
    }

}
