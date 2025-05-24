package cl.fullstack.centro_distribucion_ms.service.impl;

import cl.fullstack.centro_distribucion_ms.dto.ComunaCubiertaDTO;
//import cl.fullstack.centro_distribucion_ms.entity.CentroDistribucionEntity;
import cl.fullstack.centro_distribucion_ms.entity.ComunaCubiertaEntity;
import cl.fullstack.centro_distribucion_ms.exception.RecursoNoEncontradoException;
import cl.fullstack.centro_distribucion_ms.repository.CentroDistribucionRepository;
import cl.fullstack.centro_distribucion_ms.repository.ComunaCubiertaRepository;
import cl.fullstack.centro_distribucion_ms.response.ComunaResponse;
import cl.fullstack.centro_distribucion_ms.service.IComunaCubiertaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service // marca la clase como servicio manejado por spring
public class ComunaCubiertaServiceImpl implements IComunaCubiertaService {

    @Autowired
    private ComunaCubiertaRepository comunaCubiertaRepository; // acceso a datos de comunas cubiertas

    @Autowired
    private CentroDistribucionRepository centroDistribucionRepository; // para verificar existencia del centro

    @Autowired
    private RestTemplate restTemplate; // para hacer peticiones http al microservicio de ubicacion

    @Autowired
    private ModelMapper modelMapper; // para mapear entre entity y dto

    private static final String UBICACION_MS_API = "http://ubicacion-ms/api/comunas/"; // url del microservicio de ubicacion

    @Override
    @Transactional // asegura que la operacion sea parte de una transaccion
    public ComunaCubiertaDTO crearComunaCubierta(ComunaCubiertaDTO dto) {
        // valida que el centro exista en la base de datos (no asignamos variable para evitar warning)
        centroDistribucionRepository.findById(dto.getIdCentro())
                .orElseThrow(() -> new RecursoNoEncontradoException("centro no encontrado con id: " + dto.getIdCentro()));

        // valida que la comuna exista llamando al microservicio de ubicacion
        ComunaResponse comuna = restTemplate.getForObject(
                UBICACION_MS_API + dto.getIdComuna(),
                ComunaResponse.class
        );

        // si la respuesta es nula o no tiene nombre, lanza excepcion
        if (comuna == null || comuna.getNombre() == null) {
            throw new RecursoNoEncontradoException("comuna no encontrada con id: " + dto.getIdComuna());
        }

        // convierte el dto a entidad y lo guarda
        ComunaCubiertaEntity entity = modelMapper.map(dto, ComunaCubiertaEntity.class);
        ComunaCubiertaEntity guardado = comunaCubiertaRepository.save(entity);

        // devuelve el dto resultante
        return modelMapper.map(guardado, ComunaCubiertaDTO.class);
    }

    @Override
    public List<ComunaCubiertaDTO> obtenerTodas() {
        // obtiene todas las comunas cubiertas y las convierte a dto
        return comunaCubiertaRepository.findAll().stream()
                .map(entity -> modelMapper.map(entity, ComunaCubiertaDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ComunaCubiertaDTO obtenerPorId(Long id) {
        // busca la comuna cubierta por id, si no existe lanza excepción
        ComunaCubiertaEntity entity = comunaCubiertaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("comuna cubierta no encontrada con id: " + id));
        
        // convierte la entidad a dto y la devuelve
        return modelMapper.map(entity, ComunaCubiertaDTO.class);
    }

    @Override
    public void eliminarPorId(Long id) {
        // valida que la comuna cubierta exista
        if (!comunaCubiertaRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("comuna cubierta no encontrada con id: " + id);
        }

        // elimina la comuna cubierta por id
        comunaCubiertaRepository.deleteById(id);
    }
}

