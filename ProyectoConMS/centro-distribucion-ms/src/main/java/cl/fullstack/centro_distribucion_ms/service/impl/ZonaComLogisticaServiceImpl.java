package cl.fullstack.centro_distribucion_ms.service.impl;

import cl.fullstack.centro_distribucion_ms.dto.ZonaComLogisticaDTO;
import cl.fullstack.centro_distribucion_ms.entity.ZonaComLogisticaEntity;
import cl.fullstack.centro_distribucion_ms.exception.RecursoNoEncontradoException;
import cl.fullstack.centro_distribucion_ms.repository.ZonaComLogisticaRepository;
import cl.fullstack.centro_distribucion_ms.response.ComunaResponse;
import cl.fullstack.centro_distribucion_ms.service.IZonaComLogisticaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service // marca la clase como un servicio gestionado por spring
public class ZonaComLogisticaServiceImpl implements IZonaComLogisticaService {

    @Autowired
    private ZonaComLogisticaRepository zonaComLogisticaRepository; // acceso a datos para zona comuna logistica

    @Autowired
    private ModelMapper modelMapper; // convierte entre dto y entity

    @Autowired
    private RestTemplate restTemplate; // para hacer peticiones http a otros servicios, en esta caso declarada en resttemplateconfig/RestTemplateConfig

    private static final String UBICACION_MS_API = "http://ubicacion-ms/api/comunas/"; // url base del servicio ubicacion-ms

    @Override
    @Transactional // asegura que la operacion sea una transaccion en bd
    public ZonaComLogisticaDTO crearZonaComLogistica(ZonaComLogisticaDTO zonaDTO) {
        // valida que el nombre de la zona no este vacio ni nulo, si es asi lanza error
        validarCampoNoVacio(zonaDTO.getNombreZona(), "el nombre de la zona no puede estar vacio");
        // valida que el codigo de la zona no este vacio ni nulo, si es asi lanza error
        validarCampoNoVacio(zonaDTO.getCodigoZona(), "el codigo de la zona no puede estar vacio");

        try {
            // hace peticion get a ubicacion-ms para obtener datos de la comuna por id
            ComunaResponse comuna = restTemplate.getForObject(
                UBICACION_MS_API + zonaDTO.getIdComuna(),
                ComunaResponse.class
            );

            // si la comuna no existe o nombre es nulo lanza excepcion personalizada
            if (comuna == null || comuna.getNombre() == null) {
                throw new RecursoNoEncontradoException("la comuna no existe con id: " + zonaDTO.getIdComuna());
            }

            // asigna el nombre de la comuna al dto antes de guardar
            zonaDTO.setNombreComuna(comuna.getNombre());

            // convierte el dto a entity para guardar en bd
            ZonaComLogisticaEntity entity = modelMapper.map(zonaDTO, ZonaComLogisticaEntity.class);
            // guarda la entity y obtiene la entidad guardada
            ZonaComLogisticaEntity savedEntity = zonaComLogisticaRepository.save(entity);

            // convierte la entity guardada a dto y la retorna
            return modelMapper.map(savedEntity, ZonaComLogisticaDTO.class);

        } catch (HttpClientErrorException.NotFound ex) {
            // si no se encontro la comuna en ubicacion-ms lanza error personalizado
            throw new RecursoNoEncontradoException("la comuna no existe con id: " + zonaDTO.getIdComuna());
        }
    }

    // metodo para validar que un string no sea nulo ni vacio, si lo es lanza error con mensaje
    private void validarCampoNoVacio(String valor, String mensaje) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException(mensaje);
        }
    }

    @Override
    public List<ZonaComLogisticaDTO> obtenerZonaComLogistica() {
        // obtiene todas las entidades de la bd y las convierte a dto para devolver lista
        return zonaComLogisticaRepository.findAll().stream()
                .map(entity -> modelMapper.map(entity, ZonaComLogisticaDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ZonaComLogisticaDTO obtenerZonaComLogisticaPorId(int id) {
        // busca una entidad por id, si no la encuentra lanza error
        ZonaComLogisticaEntity entity = zonaComLogisticaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("zona no encontrada con id: " + id));
        // convierte la entidad a dto y la retorna
        return modelMapper.map(entity, ZonaComLogisticaDTO.class);
    }

    @Override
    @Transactional // asegura que la operacion de eliminacion sea una transaccion
    public void eliminarZonaComLogistica(int id) {
        // valida que la zona exista en la bd, si no lanza error
        if (!zonaComLogisticaRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("zona no encontrada con id: " + id);
        }
        // elimina la zona por id de la bd
        zonaComLogisticaRepository.deleteById(id);
    }
}
