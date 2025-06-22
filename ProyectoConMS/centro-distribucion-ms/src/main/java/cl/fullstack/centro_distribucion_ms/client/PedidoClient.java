// package cl.fullstack.centro_distribucion_ms.client;



// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.core.ParameterizedTypeReference;
// import org.springframework.http.ResponseEntity;
// import org.springframework.stereotype.Service;
// import org.springframework.web.client.HttpClientErrorException;
// import org.springframework.web.client.RestTemplate;

// import cl.fullstack.centro_distribucion_ms.dto.external.PedidoDTO;

// import org.springframework.http.HttpMethod;

// //service marca esta clase como un componente de servicio de spring. asi se detecta automaticamente en el escaneo y se registra como bean
// @Service
// //esta clase ProductoClient se encarga de comunicarse con producto-ms
// public class PedidoClient {
//     //aqui estamos inyectando automaticamente el restemplate q configuramos en RestTemplateConfig
//     @Autowired
//     private RestTemplate restTemplate;
    
//     //aqui hacemos una llamada get a la url 
//   public List<PedidoDTO> obtenerPedidosPorDespachador(int idDespachador) {
//     try {
//         ResponseEntity<List<PedidoDTO>> response = restTemplate.exchange(
//             "http://pedido-ms/api/pedidos/despachador/" + idDespachador,
//             HttpMethod.GET,
//             null,
//             new ParameterizedTypeReference<List<PedidoDTO>>() {}
//         );
//         return response.getBody();
//     } catch (HttpClientErrorException.NotFound e) {
//         return List.of(); // devuelve una lista vacía si no hay pedidos
//     }
// }
// public PedidoDTO obtenerPedidoPorId(int idPedido) {
//     try {
//         return restTemplate.getForObject("http://pedido-ms/api/pedidos/" + idPedido, PedidoDTO.class);
//     } catch (HttpClientErrorException.NotFound e) {
//         return null;
//     }
// }

// }