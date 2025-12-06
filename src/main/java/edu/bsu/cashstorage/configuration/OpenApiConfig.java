package edu.bsu.cashstorage.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Cash Storage API",
                version = "1.0.0",
                description = "API для управления личными финансами",
                contact = @Contact(
                        name = "Cash Storage Team",
                        email = "support@cashstorage.com",
                        url = "https://cashstorage.com"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://www.apache.org/licenses/LICENSE-2.0"
                ),
                termsOfService = "https://cashstorage.com/terms"
        ),
        servers = {
                @Server(
                        url = "http://localhost:8080",
                        description = "Локальный сервер"
                ),
                @Server(
                        url = "https://api.cashstorage.com",
                        description = "Продуктивный сервер"
                )
        }
)
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI();

        /*final String securitySchemeName = "bearerAuth";

        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement()
                        .addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("Введите JWT токен в формате: Bearer {token}")));*/
    }
}