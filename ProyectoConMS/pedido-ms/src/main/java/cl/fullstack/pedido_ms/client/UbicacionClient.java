package cl.fullstack.pedido_ms.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import cl.fullstack.pedido_ms.dto.external.ComunaDTO;

import org.springframework.http.HttpMethod;

//service marca esta clase como un componente de servicio de spring. asi se detecta automaticamente en el escaneo y se registra como bean
@Service
//esta clase ProductoClient se encarga de comunicarse con producto-ms
public class UbicacionClient {
    //aqui estamos inyectando automaticamente el restemplate q configuramos en RestTemplateConfig
    @Autowired
    private RestTemplate restTemplate;
    
    //aqui hacemos una llamada get a la url 
    public ComunaDTO obtenerComunasById(int comunaId) {
        return restTemplate.getForObject("http://ubicacion-ms/api/comunas/" + comunaId, ComunaDTO.class);
    }
    //tambien hace una llamada get a la url, pero obtenienndo todos en una lista, se usa .exchange porque getForObjet no doporta tipos genericos como List<ProductoDTO>
    public List<ComunaDTO> obtenerTodosLasComunas() {
        ResponseEntity<List<ComunaDTO>> response = restTemplate.exchange(
            "http://ubicacion-ms/api/comunas/",
            HttpMethod.GET,
            null,
            //este new le dice a spring que espere una lista de comunaDTO
            new ParameterizedTypeReference<List<ComunaDTO>>() {}
        );
        return response.getBody();
    }
}