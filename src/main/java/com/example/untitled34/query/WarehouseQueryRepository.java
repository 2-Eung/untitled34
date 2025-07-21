package com.example.untitled34.query;

import com.example.untitled34.model.QWarehouse;
import com.example.untitled34.model.Warehouse;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class WarehouseQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<Warehouse> search(WarehouseSearchCondition condition) {
        QWarehouse warehouse = QWarehouse.warehouse;
        BooleanBuilder builder = new BooleanBuilder();

        if (condition.getName() != null && !condition.getName().isBlank()) {
            builder.and(warehouse.name.containsIgnoreCase(condition.getName()));
        }
        if (condition.getLocation() != null && !condition.getLocation().isBlank()) {
            builder.and(warehouse.location.containsIgnoreCase(condition.getLocation()));
        }

        return queryFactory.selectFrom(warehouse).where(builder).orderBy(warehouse.name.asc()).fetch(); // name 기준 정렬
    }
}
