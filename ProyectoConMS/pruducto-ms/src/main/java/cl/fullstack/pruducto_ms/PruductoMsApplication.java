package cl.fullstack.pruducto_ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDiscoveryClient
@SpringBootApplication
public class PruductoMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(PruductoMsApplication.class, args);
	}

}
