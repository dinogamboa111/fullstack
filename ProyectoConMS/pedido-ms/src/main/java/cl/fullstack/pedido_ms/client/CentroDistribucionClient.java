package cl.fullstack.pedido_ms.client;

import org.springframework.stereotype.Component;

import org.springframework.web.client.RestTemplate;

import cl.fullstack.pedido_ms.dto.external.CentroDistribucionDTO;

@Component
public class CentroDistribucionClient {

    private final RestTemplate restTemplate;

    public CentroDistribucionClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // private static final String CENTRO_SERVICE = "http://centro-distribucion-ms";

    public CentroDistribucionDTO obtenerCentroPorComuna(int idComuna) {
        CentroDistribucionDTO centro = restTemplate.getForObject(
                "http://centro-distribucion-ms/api/centros-distribucion/comuna/" + idComuna,
                CentroDistribucionDTO.class);
        if (centro == null) {
            throw new RuntimeException("No se encontró centro para la comuna: " + idComuna);
        }
        return centro;
    }
}
