package com.nyg.sideproj.exception;

import lombok.Builder;
import lombok.Data;

/**
 * 에러 응답 DTO
 */
@Data
@Builder
public class ErrorResponse {
    private String code;
    private String message;
    private String detail;
}