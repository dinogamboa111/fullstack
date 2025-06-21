package cl.fullstack.usuario_ms.Client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import cl.fullstack.usuario_ms.dto.external.CentroDistribucionDTO;

import java.util.Optional;

@Service
public class CentroDistribucionClient {

    @Autowired
    private RestTemplate restTemplate;

    // Base URL de centro-distribucion-ms en Eureka (nombre del servicio)
    private static final String CENTRO_SERVICE = "http://centro-distribucion-ms";

    public Optional<CentroDistribucionDTO> obtenerCentroPorComuna(int idComuna) {
        try {
            String url = CENTRO_SERVICE + "/api/centros-distribucion/comuna/" + idComuna;
            CentroDistribucionDTO centro = restTemplate.getForObject(url, CentroDistribucionDTO.class);
            return Optional.ofNullable(centro);
        } catch (Exception e) {
            // Mejor loguear el error para debug
            System.err.println("Error al obtener centro: " + e.getMessage());
            return Optional.empty();
        }
    }
}