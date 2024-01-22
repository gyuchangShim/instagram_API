package com.instagram.api.util.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "COW Instagram 클론 코딩 API",
                description = "COW Instagram 클론 코딩 API 명세",
                version = "v1"))
@Configuration
public class SwaggerConfig {

    private static final String SECURITY_SCHEMA_NAME = "authorization";

    @Bean
    public OpenAPI chatOpenApi() {
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER).name("Authorization");
        SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearerAuth");

        return new OpenAPI()
                .components(new Components().addSecuritySchemes(SECURITY_SCHEMA_NAME, new SecurityScheme()
                        .name(SECURITY_SCHEMA_NAME)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")))
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEMA_NAME))
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("인스타그램 클론 코딩 API")
                        .description("인스타그램 클론 코딩 연습을 위한 API")
                        .version("1.0.0"));
    }

}
