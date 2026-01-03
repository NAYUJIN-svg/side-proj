package com.nyg.sideproj.exception;

/**
 * 데이터베이스 관련 예외
 */
public class DatabaseException extends RuntimeException {
    public DatabaseException(String message) {
        super(message);
    }
    
    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}