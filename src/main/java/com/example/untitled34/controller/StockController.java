package com.example.untitled34.controller;

import com.example.untitled34.dto.StockDto;
import com.example.untitled34.dto.StockUpdateDto;
import com.example.untitled34.model.Stock;
import com.example.untitled34.query.StockSearchCondition;
import com.example.untitled34.service.StockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stocks")
@RequiredArgsConstructor
public class StockController {
    private final StockService stockService;

    @PostMapping
    public Stock create(@Valid @RequestBody StockDto dto) {
        return stockService.create(dto);
    }

    @GetMapping
    public List<Stock> search(StockSearchCondition condition) {
        return stockService.search(condition);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        stockService.delete(id);
    }

    @PutMapping("/{id}")
    public Stock update(@PathVariable Long id, @Valid @RequestBody StockUpdateDto dto) {
        return stockService.update(id, dto);
    }

    @PostMapping("/transfer")
    public void transfer(
            @RequestParam Long productId,
            @RequestParam Long srcWarehouseId,
            @RequestParam Long destWarehouseId,
            @RequestParam int quantity
    ) {
        stockService.transferStock(productId, srcWarehouseId, destWarehouseId, quantity);
    }
}
