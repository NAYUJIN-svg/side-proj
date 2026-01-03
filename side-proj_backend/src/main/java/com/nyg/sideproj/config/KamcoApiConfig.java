package com.nyg.sideproj.config;

import lombok.Getter;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class KamcoApiConfig {

    private final String baseUrl;
    private final String serviceKey;

    public KamcoApiConfig() {
        this.baseUrl = System.getenv("KAMCO_BASE_URL");
        this.serviceKey = System.getenv("KAMCO_SERVICE_KEY");

        if (baseUrl == null || serviceKey == null) {
            System.out.println("⚠ KAMCO API 환경변수 없음 → API 호출 스킵");
        }
    }

    public boolean isEnabled() {
        return baseUrl != null && serviceKey != null;
    }
}
