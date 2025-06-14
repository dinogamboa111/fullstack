package cl.fullstack.pruducto_ms.client;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import cl.fullstack.pruducto_ms.dto.ClienteDTO;

import org.springframework.http.HttpMethod;

//service marca esta clase como un componente de servicio de spring. asi se detecta automaticamente en el escaneo y se registra como bean
@Service
//esta clase ClienteClient se encarga de comunicarse con cliente-ms
public class ClienteClient {
    //aqui estamos inyectando automaticamente el restemplate q configuramos en RestTemplateConfig
    @Autowired
    private RestTemplate restTemplate;
    
    //aqui hacemos una llamada get a la url 
    public ClienteDTO obtenerClienteByRut(int clienteRut) {
        return restTemplate.getForObject("http://cliente-service/api/clientes/" + clienteRut, ClienteDTO.class);
    }
    //tambien hace una llamada get a la url, pero obtenienndo todos en una lista, se usa .exchange porque getForObjet no doporta tipos genericos como List<ProductoDTO>
    public List<ClienteDTO> obtenerTodosLosProductos() {
        ResponseEntity<List<ClienteDTO>> response = restTemplate.exchange(
            "http://cliente-service/api/clientes",
            HttpMethod.GET,
            null,
            //este new le dice a spring que espere una lista de productodto
            new ParameterizedTypeReference<List<ClienteDTO>>() {}
        );
        return response.getBody();
    }
}