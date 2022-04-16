package com.example.api.configuration.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public abstract class SwaggerConfiguration {

    public static final String SECURITY_SCHEME_NAME = "bearerAuth";

    @Value("${application.swagger.host:}")
    private String hostUri;

    protected String getHostUri() {
        return hostUri;
    }

    protected abstract Info getInfo();

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
            .addServersItem(
                new Server().url(getHostUri()))
            .components(
                new Components()
                    .addSecuritySchemes(SECURITY_SCHEME_NAME,
                        new SecurityScheme()
                            .name(SECURITY_SCHEME_NAME)
                            .type(SecurityScheme.Type.HTTP)
                            .scheme("bearer")
                            .bearerFormat("JWT")
                    )
            )
            .info(getInfo())
            .externalDocs(
                new ExternalDocumentation()
                    .description("Документация")
                    .url(""));
    }

}
