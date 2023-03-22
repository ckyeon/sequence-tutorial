package com.sequence.sequencetutorial.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

  @Bean
  public OpenAPI customOpenApi() {
    SecurityScheme jwtSecurityScheme = new SecurityScheme()
      .type(Type.HTTP)
      .scheme("Bearer")
      .bearerFormat("JWT");

    Components securityComponents = new Components()
      .addSecuritySchemes("Authorization", jwtSecurityScheme);

    return new OpenAPI()
      .components(securityComponents);
  }
}
