package com.example.untitled34.service;

import com.example.untitled34.dto.StockDto;
import com.example.untitled34.dto.StockUpdateDto;
import com.example.untitled34.model.Product;
import com.example.untitled34.model.Stock;
import com.example.untitled34.model.Warehouse;
import com.example.untitled34.query.StockQueryRepository;
import com.example.untitled34.query.StockSearchCondition;
import com.example.untitled34.repository.ProductRepository;
import com.example.untitled34.repository.StockRepository;
import com.example.untitled34.repository.WarehouseRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class StockService {
    private final StockRepository stockRepository;
    private final StockQueryRepository queryRepository;
    private final ProductRepository productRepository;              // product 가 존재하는지 확인용
    private final WarehouseRepository warehouseRepository;          // warehouse 가 존재하는지 확인용

    public Stock create(StockDto dto) {                                                   // 여기 dto 는 controller 로부터 받아옴
        Product product = productRepository.findById(dto.getProductId())                  // 받아온게 조건에 맞으면  product 에 넣음
                .orElseThrow(() -> new NoSuchElementException("제품을 찾을 수 없습니다."));   // 어떻게 가능하냐?? productRepository 의 반환타입은 Product 임
        Warehouse warehouse = warehouseRepository.findById(dto.getWarehouseId())
                .orElseThrow(() -> new NoSuchElementException("창고를 찾을 수 없습니다."));

        Stock stock = new Stock();
        stock.setProduct(product);
        stock.setWarehouse(warehouse);
        stock.setQuantity(dto.getQuantity());
        return stockRepository.save(stock);
    }

    public List<Stock> search(StockSearchCondition condition) {
        return queryRepository.search(condition);
    }

    public Stock getById(Long id) {
        return stockRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("재고를 찾을 수 없습니다."));
    }

    public void delete(Long id) {
        stockRepository.deleteById(id);
    }

    public Stock update(Long id, StockUpdateDto dto) {
        Stock stock = getById(id);
        stock.setQuantity(dto.getQuantity());
        return stockRepository.save(stock);
    }
                                         // 특정제품 , 보내는창고 , 받는창고 , 보내는 수량
    public void transferStock(Long productId, Long srcWarehouseId, Long destWarehouseId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("제품을 찾을 수 없습니다."));
        Warehouse srcWarehouse = warehouseRepository.findById(srcWarehouseId)
                .orElseThrow(() -> new NoSuchElementException("보내는창고를 찾을 수 없습니다."));
        Warehouse destWarehouse = warehouseRepository.findById(destWarehouseId)
                .orElseThrow(() -> new NoSuchElementException("받는창고를 찾을 수 없습니다."));

                                // stockRepository 에 따로 만들어 놓은것을 이제야 여기서 드디어 마참내 쓴다.
        Stock srcStock = stockRepository.findByProductIdAndWarehouseId(productId, srcWarehouseId)
                .orElseThrow(() -> new IllegalStateException("출발 재고가 존재하지 않습니다."));
        if (srcStock.getQuantity() < quantity) {
            throw new IllegalStateException("출발 재고가 부족합니다.");
        }
        srcStock.setQuantity(srcStock.getQuantity() - quantity);

        Stock destStock = stockRepository.findByProductIdAndWarehouseId(productId, destWarehouseId)
                .orElse(new Stock());
        destStock.setProduct(product);
        destStock.setWarehouse(destWarehouse);
        destStock.setQuantity(destStock.getQuantity() == null ? quantity : destStock.getQuantity() + quantity);

        stockRepository.saveAll(List.of(srcStock, destStock));
    }
}