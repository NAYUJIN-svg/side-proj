package com.nyg.sideproj.exception;

/**
 * KAMCO API 관련 예외
 */
public class KamcoApiException extends RuntimeException {
    public KamcoApiException(String message) {
        super(message);
    }
    
    public KamcoApiException(String message, Throwable cause) {
        super(message, cause);
    }
}