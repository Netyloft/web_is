package com.example.api.configuration.swagger;

import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class PublicSwaggerConfiguration extends SwaggerConfiguration {

    @Override
    protected Info getInfo() {
        return new Info()
            .title("WebIs Control API")
            .description("Forum")
            .version("v0.0.1");
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
            .group("Все")
            .pathsToMatch("/**")
            .addOpenApiCustomiser(new ApiSecurityCustomizer())
            .addOperationCustomizer(new OperationFilterCustomizer())
            .build();
    }
}
