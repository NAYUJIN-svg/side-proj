package com.nyg.sideproj.exception;

import com.nyg.sideproj.constants.AppConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

/**
 * 전역 예외 처리 핸들러
 * - 비즈니스 예외만 ERROR
 * - 검증 오류는 WARN
 * - 정적 리소스(favicon 등)는 무시
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /* =================================================
       KAMCO API 오류
    ================================================= */
    @ExceptionHandler(KamcoApiException.class)
    public ResponseEntity<ErrorResponse> handleKamcoApiException(KamcoApiException e) {
        log.error("KAMCO API 오류", e);

        return ResponseEntity
                .status(HttpStatus.BAD_GATEWAY)
                .body(ErrorResponse.builder()
                        .code(AppConstants.ErrorCode.KAMCO_API_ERROR)
                        .message("KAMCO API 호출 중 오류가 발생했습니다")
                        .detail(e.getMessage())
                        .build());
    }

    /* =================================================
       DB 오류
    ================================================= */
    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<ErrorResponse> handleDatabaseException(DatabaseException e) {
        log.error("데이터베이스 오류", e);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.builder()
                        .code(AppConstants.ErrorCode.DATABASE_ERROR)
                        .message("데이터베이스 처리 중 오류가 발생했습니다")
                        .detail(e.getMessage())
                        .build());
    }

    /* =================================================
       Validation 오류 (@Valid, @Validated)
    ================================================= */
    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public ResponseEntity<ErrorResponse> handleValidationException(Exception e) {
        log.warn("유효성 검증 실패: {}", e.getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.builder()
                        .code(AppConstants.ErrorCode.VALIDATION_ERROR)
                        .message("입력값 검증에 실패했습니다")
                        .detail("요청 파라미터를 확인해주세요")
                        .build());
    }

    /* =================================================
       파라미터 타입 오류
    ================================================= */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.warn("파라미터 타입 오류: {}", e.getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.builder()
                        .code(AppConstants.ErrorCode.VALIDATION_ERROR)
                        .message("파라미터 타입이 올바르지 않습니다")
                        .detail(String.format("파라미터 '%s'의 값이 올바르지 않습니다", e.getName()))
                        .build());
    }

    /* =================================================
       정적 리소스 없음 (favicon.ico 등)
       - 로그 ❌
       - 조용히 404
    ================================================= */
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Void> handleNoResourceFound(NoResourceFoundException e) {
        return ResponseEntity.notFound().build();
    }

    /* =================================================
       예상치 못한 모든 예외
       - 여기만 ERROR
    ================================================= */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception e) {
        log.error("서버 내부 오류", e);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.builder()
                        .code(AppConstants.ErrorCode.INTERNAL_SERVER_ERROR)
                        .message("서버 내부 오류가 발생했습니다")
                        .detail("잠시 후 다시 시도해주세요")
                        .build());
    }
}
