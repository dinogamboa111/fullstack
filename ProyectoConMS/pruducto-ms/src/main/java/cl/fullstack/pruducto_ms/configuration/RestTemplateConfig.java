package cl.fullstack.pruducto_ms.configuration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

//esta anotacion, indica que es una clase de configuracion de Spring, es decir, contiene uno o mas bean para ser
//gestionados por spring
@Configuration
public class RestTemplateConfig {
     //@bean le dice a spring q este metodo retorna un bean, con esto se crea una unica instancia de restemplate y se podra en cualquier parte q use @autowired
    @Bean
    //@loadbalanced es modifica internamente restemplate para que soporte balanceo de carga usando el nombre registrado en eureka, con esto se permite usar direcciones
    //como el nombre de registro del ms en ves de la url con el puerto ej: producto-ms en evz de localhost:8920....
    @LoadBalanced

    //restTemplateBuilder construye una inmstancia de resTemplate
    public RestTemplate restTemplate(RestTemplateBuilder builder){
        return builder.build();
    }
     

}
