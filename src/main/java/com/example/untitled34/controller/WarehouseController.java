package com.example.untitled34.controller;


import com.example.untitled34.dto.WarehouseDto;


import com.example.untitled34.model.Product;
import com.example.untitled34.model.Warehouse;
import com.example.untitled34.query.ProductSearchCondition;
import com.example.untitled34.query.WarehouseSearchCondition;
import com.example.untitled34.service.WarehouseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/warehouses")
@RequiredArgsConstructor
public class WarehouseController {
    private final WarehouseService warehouseService;

    @PostMapping
    public Warehouse create(@Valid @RequestBody WarehouseDto dto) {
        return warehouseService.create(dto);
    }

    @GetMapping
    public List<Warehouse> search(WarehouseSearchCondition condition) {
        return warehouseService.search(condition);
    }

    @GetMapping("/{id}")
    public Warehouse getById(@PathVariable Long id) {
        return warehouseService.getById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        warehouseService.delete(id);
    }

    @PutMapping("/{id}")
    public Warehouse update(@PathVariable Long id, @Valid @RequestBody WarehouseDto dto) {
        return warehouseService.update(id, dto);
    }
}
