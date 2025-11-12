package com.nyg.sideproj.service;

import com.nyg.sideproj.config.KamcoApiConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * KAMCO API 호출 전담 클라이언트
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class KamcoApiClient {
    
    private final WebClient.Builder webClientBuilder;
    private final KamcoApiConfig config;
    
    public String fetchData(int pageNo, int numOfRows) {
        return webClientBuilder.build()
            .get()
            .uri(uriBuilder -> uriBuilder
                .scheme(config.getScheme())
                .host(config.getHost())
                .path(config.getPath())
                .queryParam("serviceKey", config.getServiceKey())
                .queryParam("pageNo", pageNo)
                .queryParam("numOfRows", numOfRows)
                .build())
            .retrieve()
            .bodyToMono(String.class)
            .block();
    }
}
