package com.eco.system.security;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    /**
     * Configures the OpenAPI documentation for the application.
     * 
     * @return an OpenAPI instance with customized settings
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            // Set the title and version for the API documentation
            .info(new Info().title("API Documentation").version("1.0"))
            // Add security requirements to the API documentation, specifying BearerAuth
            .addSecurityItem(new SecurityRequirement().addList("BearerAuth"))
            .components(new io.swagger.v3.oas.models.Components()
                // Define the security scheme for BearerAuth
                .addSecuritySchemes("BearerAuth", new SecurityScheme()
                    .type(SecurityScheme.Type.HTTP)  // Define the security scheme type as HTTP
                    .scheme("bearer")                // Specify the authentication scheme as bearer
                    .bearerFormat("JWT")));          // Set the format of the bearer token to JWT
    }
}
