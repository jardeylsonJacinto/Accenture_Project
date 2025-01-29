package br.com.api_gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springdoc.core.models.GroupedOpenApi;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi loginServiceApi() {
        return GroupedOpenApi.builder()
                .group("login-service")
                .pathsToMatch("/auth/**")
                .build();
    }

    @Bean
    public GroupedOpenApi userServiceApi() {
        return GroupedOpenApi.builder()
                .group("user-service")
                .pathsToMatch("/user/**")
                .build();
    }
}
