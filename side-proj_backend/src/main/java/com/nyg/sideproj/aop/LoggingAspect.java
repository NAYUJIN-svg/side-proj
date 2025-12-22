package com.nyg.sideproj.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * 로깅 AOP
 * - Controller, Service 메서드 실행 시간 측정
 */
@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Around("execution(* com.nyg.sideproj.controller..*(..))")
    public Object logController(ProceedingJoinPoint joinPoint) throws Throwable {
        return logExecution(joinPoint, "CONTROLLER");
    }

    @Around("execution(* com.nyg.sideproj.service..*(..))")
    public Object logService(ProceedingJoinPoint joinPoint) throws Throwable {
        return logExecution(joinPoint, "SERVICE");
    }

    private Object logExecution(ProceedingJoinPoint joinPoint, String layer) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        long start = System.currentTimeMillis();
        
        try {
            log.info("[{}] 시작: {}", layer, methodName);
            Object result = joinPoint.proceed();
            long elapsed = System.currentTimeMillis() - start;
            log.info("[{}] 종료: {} ({}ms)", layer, methodName, elapsed);
            return result;
        } catch (Exception e) {
            long elapsed = System.currentTimeMillis() - start;
            log.error("[{}] 오류: {} ({}ms) - {}", layer, methodName, elapsed, e.getMessage());
            throw e;
        }
    }
}
