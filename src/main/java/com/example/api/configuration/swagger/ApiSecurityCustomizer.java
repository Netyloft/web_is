package com.example.api.configuration.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springdoc.core.customizers.OpenApiCustomiser;

public class ApiSecurityCustomizer implements OpenApiCustomiser {

    @Override
    public void customise(OpenAPI api) {
        api.addSecurityItem(new SecurityRequirement().addList(SwaggerConfiguration.SECURITY_SCHEME_NAME));
    }

}
