package cl.fullstack.centro_distribucion_ms.resttemplateconfig;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
    @Bean
    @LoadBalanced  // permite resolver "ubicacion-ms" via eureka para balancear cargas entre instancias
    public RestTemplate restTemplate() {
        return new RestTemplate();  // crea un objeto para hacer llamadas http al microservicio ubicacion-ms que da datos de comunas
    }
}
