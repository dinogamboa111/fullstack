package cl.fullstack.pedido_ms.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import cl.fullstack.pedido_ms.dto.external.UsuarioDTO;

import org.springframework.http.HttpMethod;

//service marca esta clase como un componente de servicio de spring. asi se detecta automaticamente en el escaneo y se registra como bean
@Service
//esta clase ProductoClient se encarga de comunicarse con producto-ms
public class UsuarioClient {
    //aqui estamos inyectando automaticamente el restemplate q configuramos en RestTemplateConfig
    @Autowired
    private RestTemplate restTemplate;
    
    //aqui hacemos una llamada get a la url 
    public UsuarioDTO obtenerUsuariosById(int usuarioId) {
        return restTemplate.getForObject("http://usuario-service/api/usuarios/" + usuarioId, UsuarioDTO.class);
    }
    //tambien hace una llamada get a la url, pero obtenienndo todos en una lista, se usa .exchange porque getForObjet no doporta tipos genericos como List<UsuarioDTO>
    public List<UsuarioDTO> obtenerTodosLosUsuarios() {
        ResponseEntity<List<UsuarioDTO>> response = restTemplate.exchange(
            "http://usuario-service/api/usuarios/",
            HttpMethod.GET,
            null,
            //este new le dice a spring que espere una lista de UusuarioDTO
            new ParameterizedTypeReference<List<UsuarioDTO>>() {}
        );
        return response.getBody();
    }
}
