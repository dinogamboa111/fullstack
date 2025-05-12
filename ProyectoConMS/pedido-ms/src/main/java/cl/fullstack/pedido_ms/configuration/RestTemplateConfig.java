package cl.fullstack.pedido_ms.configuration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

//spring apenas levante el servidor ejecutara lo que este en esta clase antes que todo el resto
@Configuration

public class RestTemplateConfig {

    @Bean
    @LoadBalanced
    public RestTemplate resTemplate(RestTemplateBuilder builder){
        return builder.build();
        
    }





}
