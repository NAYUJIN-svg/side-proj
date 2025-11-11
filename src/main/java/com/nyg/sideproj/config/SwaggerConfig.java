package com.nyg.sideproj.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Kamco Data API")
                        .description("캠코 공공데이터 조회 API")
                        .version("1.0.0"))
                .servers(List.of(
                        new Server().url("http://localhost:8001").description("Local Server")
                ));
    }
}
