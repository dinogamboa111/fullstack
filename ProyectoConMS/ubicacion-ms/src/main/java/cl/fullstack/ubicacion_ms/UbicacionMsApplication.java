package cl.fullstack.ubicacion_ms;


import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@EnableDiscoveryClient  // REGISTRO  Eureka
@SpringBootApplication
public class UbicacionMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(UbicacionMsApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}