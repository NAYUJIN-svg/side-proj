package com.nyg.sideproj.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * KAMCO API 설정
 */
@Getter
@Configuration
public class KamcoApiConfig {
    
    @Value("${kamco.api.scheme:http}")
    private String scheme;
    
    @Value("${kamco.api.host:openapi.onbid.co.kr}")
    private String host;
    
    @Value("${kamco.api.path:/openapi/services/KamcoPblsalThingInquireSvc/getKamcoPbctCltrList}")
    private String path;
    
    @Value("${kamco.api.limit:10}")
    private int limit;
    
    @Value("${kamco.api.max-pages:10}")
    private int maxPages;
    
    @Value("${kamco.api.items-per-page:50}")
    private int itemsPerPage;
    
    public String getServiceKey() {
        String key = System.getenv("KAMCO_SERVICE_KEY");
        if (key == null || key.isEmpty()) {
            throw new IllegalStateException("KAMCO_SERVICE_KEY 환경변수가 설정되지 않았습니다!");
        }
        return key;
    }
}
