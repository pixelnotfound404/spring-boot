package com.kunthea.phoneshop.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI phoneShopOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Phone Shop API")
                        .description("API documentation for Phone Shop application")
                        .version("1.0.0"));
    }
}