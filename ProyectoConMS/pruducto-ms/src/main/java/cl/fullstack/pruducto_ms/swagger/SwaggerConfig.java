package cl.fullstack.pruducto_ms.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Microservicio Producto")
                .version("1.0.0")
                .description("Microservicio dedicado a la gestión de productos, incluyendo su categoria.")
                .contact(new Contact()
                    .name("Integrantes del grupo: Camila Erices - Dino Gamboa - Jaime Delgadillo")                  
                    .url("https://github.com/dinogamboa111/fullstack.git")
                )
            );
    }
}
