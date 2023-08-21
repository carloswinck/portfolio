package com.code.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Portfolio API", version = "1.0", description = "Documentation Portfolio API v1.0"))
public class OpenAPI30Configuration {

}