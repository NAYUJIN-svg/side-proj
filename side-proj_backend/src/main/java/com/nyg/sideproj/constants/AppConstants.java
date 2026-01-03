package com.nyg.sideproj.constants;

/**
 * 애플리케이션 상수 정의
 */
public final class AppConstants {
    
    private AppConstants() {
        // 유틸리티 클래스
    }
    
    // API 관련 상수
    public static final class Api {
        public static final String BASE_PATH = "/api/kamco";
        public static final int DEFAULT_PAGE_NO = 1;
        public static final int DEFAULT_PAGE_SIZE = 10;
        public static final int MAX_PAGE_SIZE = 100;
    }
    
    // 에러 코드
    public static final class ErrorCode {
        public static final String KAMCO_API_ERROR = "KAMCO_API_ERROR";
        public static final String DATABASE_ERROR = "DATABASE_ERROR";
        public static final String VALIDATION_ERROR = "VALIDATION_ERROR";
        public static final String INTERNAL_SERVER_ERROR = "INTERNAL_SERVER_ERROR";
    }
    
    // 메시지
    public static final class Message {
        public static final String SUCCESS = "성공";
        public static final String DB_CONNECTION_SUCCESS = "DB 연결 성공";
        public static final String DB_CONNECTION_FAILED = "DB 연결 실패";
        public static final String DATA_INIT_SUCCESS = "데이터 초기화 완료";
        public static final String SAMPLE_DATA_CREATED = "샘플 데이터 생성 완료";
    }
}