package com.example.untitled34.query;

import com.example.untitled34.model.Product;
import com.example.untitled34.model.QProduct;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductQueryRepository {
    private final JPAQueryFactory queryFactory; // 쿼리 생성 담당

    public List<Product> search(ProductSearchCondition condition) {
        QProduct product = QProduct.product;
        BooleanBuilder builder = new BooleanBuilder(); //  쿼리 DSL 에 들어있는 기능 : 쿼리 조건을 만들고 이를 만족하는지 판단.

        if(condition.getName() != null && !condition.getName().isBlank()) {    // null 이 아니고 비어있지 않아야 한다.
            builder.and(product.name.containsIgnoreCase(condition.getName())); // contains : 해당 문자열이 있느지 확인 , ignoreCase : 대소문자는 무시하겠다.
        }

        if(condition.getMinPrice() != null) {
            builder.and(product.price.goe(condition.getMinPrice())); // goe : 크거나 같거나 (grater or equal)
        }

        if(condition.getMaxPrice() != null) {
            builder.and(product.price.loe(condition.getMaxPrice())); // loe : 작거나 같거나 (lower or equal)
        }

        return queryFactory.selectFrom(product).where(builder).orderBy(product.name.asc()).fetch();  // product 의 name 을 기준으로 오름차순 (가나다순)
    }
}
