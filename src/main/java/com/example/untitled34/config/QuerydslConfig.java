package com.example.untitled34.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class QuerydslConfig {                       // config : 설정파일
    private final EntityManager entityManager;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {      // @RequiredArgsConstructor 로 생성자를 만들어서 @Bean 을 넣어 다른곳에서도 Spring 이 자동으로 객체를 만들게 설정하였음
        return new JPAQueryFactory(entityManager);
    }
}