package com.hintro.meetingintelligence.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        final String securitySchemeName =
                "bearerAuth";

        return new OpenAPI()

                .info(
                        new Info()

                                .title(
                                        "Meeting Intelligence API"
                                )

                                .version(
                                        "v1.0"
                                )

                                .description("""
                                        AI-powered Meeting Intelligence Platform.
                                        
                                        Features:
                                        - AI meeting transcript analysis
                                        - Citation grounded summaries
                                        - Action item extraction
                                        - Overdue task tracking
                                        - Discord reminder integration
                                        - JWT authentication
                                        - Scheduled reminder workflows
                                        
                                        Secure all protected APIs using JWT Bearer Token.
                                        """)

                                .contact(
                                        new Contact()
                                                .name(
                                                        "Arshiya Shaikh"
                                                )
                                                .email(
                                                        "arshiyashaikh2202@gmail.com"
                                                )
                                )
                )

                .addSecurityItem(
                        new SecurityRequirement()
                                .addList(
                                        securitySchemeName
                                )
                )

                .components(
                        new Components()
                                .addSecuritySchemes(
                                        securitySchemeName,

                                        new SecurityScheme()
                                                .name(
                                                        securitySchemeName
                                                )
                                                .type(
                                                        SecurityScheme.Type.HTTP
                                                )
                                                .scheme(
                                                        "bearer"
                                                )
                                                .bearerFormat(
                                                        "JWT"
                                                )
                                                .description("""
                                                        Enter JWT token in this format:
                                                        
                                                        Bearer <your-token>
                                                        """)
                                )
                );
    }
}