package cl.fullstack.centro_distribucion_ms.Client;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import cl.fullstack.centro_distribucion_ms.dto.external.PedidoDTO;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

//marcamos esta clase como servicio para que spring la gestione como un bean
@Service
public class PedidoClient {
    // inyectamos resttemplate
    @Autowired
    private RestTemplate restTemplate; //

    public List<PedidoDTO> obtenerPedidosPorDespachador(int idDespachador) {
        try {
            // llamada http al pedido-ms
            ResponseEntity<List<PedidoDTO>> response = restTemplate.exchange(
                    "http://pedido-ms/api/pedidos/despachador/" + idDespachador,
                    HttpMethod.GET,
                    null,
                    // definimos el tipo de respuesta esperada: una lista de pedidodTO
                    new ParameterizedTypeReference<List<PedidoDTO>>() {
                    });
            // retorna el cuerpo de la respuesta(lista de pedidos)
            return response.getBody();
            // capturamos cualquier exepcion que ocurra
        } catch (Exception e) {
            e.printStackTrace(); // imprimimos el error por consola para saber
            // retorna una lista vacia para evitar que la app falle en caso de error
            return Collections.emptyList();
        }
    }

}
