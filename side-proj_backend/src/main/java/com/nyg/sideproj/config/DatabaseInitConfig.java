package com.nyg.sideproj.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * DB 초기화 설정
 * - 테이블 생성 로직 제거
 * - DDL은 schema.sql 또는 수동 관리
 */
@Slf4j
@Configuration
public class DatabaseInitConfig {

    @Bean
    public ApplicationRunner databaseInitializer() {
        return args -> {
            log.info("DatabaseInitConfig 실행됨 - 테이블 생성 로직 없음");
        };
    }
}
