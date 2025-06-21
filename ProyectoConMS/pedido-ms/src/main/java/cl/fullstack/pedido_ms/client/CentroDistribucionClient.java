package cl.fullstack.pedido_ms.client;

import java.util.Optional;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import cl.fullstack.pedido_ms.dto.external.CentroDistribucionDTO;

@Component
public class CentroDistribucionClient {

    private final RestTemplate restTemplate;

    public CentroDistribucionClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Optional<CentroDistribucionDTO> obtenerCentroPorComuna(int idComuna) {
        try {
            CentroDistribucionDTO centro = restTemplate.getForObject(
                "http://CENTRO-DISTRIBUCION-MS/api/centros-distribucion/comuna/" + idComuna,
                CentroDistribucionDTO.class);
            return Optional.ofNullable(centro);
        } catch (HttpClientErrorException.NotFound e) {
            // No se encontró el centro para la comuna
            return Optional.empty();
        } catch (Exception e) {
            // Aquí podrías manejar otras excepciones o re-lanzar
            throw e;
        }
    }
}
