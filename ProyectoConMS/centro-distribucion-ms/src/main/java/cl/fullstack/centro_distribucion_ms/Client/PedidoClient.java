package cl.fullstack.centro_distribucion_ms.Client;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import cl.fullstack.centro_distribucion_ms.dto.PedidoDTO;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class PedidoClient {

    @Autowired
    private RestTemplate restTemplate;  // ✅ Inyecta el bean con @LoadBalanced
public List<PedidoDTO> obtenerPedidosPorDespachador(Long idDespachador) {
    try {
        ResponseEntity<List<PedidoDTO>> response = restTemplate.exchange(
            "http://pedido-ms/api/pedidos/despachador/" + idDespachador,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<PedidoDTO>>() {}
        );
        return response.getBody();
    } catch (Exception e) {
        e.printStackTrace();  // <--- agrega para ver qué error ocurre si falla
        return Collections.emptyList();
    }
}

}
