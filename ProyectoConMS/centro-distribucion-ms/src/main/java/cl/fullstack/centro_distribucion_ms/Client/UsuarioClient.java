package cl.fullstack.centro_distribucion_ms.Client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import cl.fullstack.centro_distribucion_ms.dto.UsuarioDTO;

@Service
public class UsuarioClient {

    @Autowired
    private RestTemplate restTemplate;  // 

    public UsuarioDTO obtenerUsuarioPorId(int id) {
        try {
            return restTemplate.getForObject(
                "http://usuario-service/api/usuarios/usuario-id/" + id,
                UsuarioDTO.class
            );
        } catch (Exception e) {
            System.err.println("ERROR al llamar a usuario-service: " + e.getMessage());
            return null;
        }
    }
}
