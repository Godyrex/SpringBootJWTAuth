package com.example.springbootjwtauth.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        security = {
                @SecurityRequirement(
                        name = "BearerAuth"
                )
        }
)
public class OpenAPIConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(infoAPI()).schemaRequirement("BearerAuth",securityScheme());

    }

    public Info infoAPI() {
        return new Info().title("Spring Boot JWT auth")
                .description("JWT authentication with user profile and admin panel")
                .contact(contactAPI());
    }

    public Contact contactAPI() {
        return new Contact().name("Oussema Ouakad")
                .email("oussouakad@gmail.com")
                .url("https://www.linkedin.com/in/ouakadoussema/");
    }
    public SecurityScheme securityScheme(){
        return new SecurityScheme()
                .name("BearerAuth")
                .scheme("bearer")
                .type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER);
    }


    @Bean
    public GroupedOpenApi productPublicApi() {
        return GroupedOpenApi.builder()
                .group("Spring Boot JWT Auth API")
                .pathsToMatch("/**/**")
                .pathsToExclude("**")
                .build();
    }


}
