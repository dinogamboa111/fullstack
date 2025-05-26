package cl.fullstack.centro_distribucion_ms.service.impl;

import cl.fullstack.centro_distribucion_ms.dto.ComunaCubiertaDTO;
import cl.fullstack.centro_distribucion_ms.entity.ComunaCubiertaEntity;
import cl.fullstack.centro_distribucion_ms.entity.ComunaCubiertaPKcompuesta;
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

@Service // marca esta clase como servicio manejado por spring
public class ComunaCubiertaServiceImpl implements IComunaCubiertaService {

    @Autowired
    private ComunaCubiertaRepository comunaCubiertaRepository; // repositorio para comuna cubierta

    @Autowired
    private CentroDistribucionRepository centroDistribucionRepository; // repositorio para centro de distribucion

    @Autowired
    private RestTemplate restTemplate; // cliente http para llamar al microservicio de ubicacion

    @Autowired
    private ModelMapper modelMapper; // util para mapear entre dto y entity

    private static final String UBICACION_MS_API = "http://ubicacion-ms/api/comunas/"; // url base del microservicio

    @Override
    @Transactional // asegura que la operacion sea transaccional (guarda los datos solo si todo funciona bien, si hay error no se guarda nada)
    public ComunaCubiertaDTO crearComunaCubierta(ComunaCubiertaDTO dto) {
        // valida que el centro de distribucion exista
        centroDistribucionRepository.findById(dto.getIdCentro())
                .orElseThrow(() -> new RecursoNoEncontradoException("centro no encontrado con id: " + dto.getIdCentro()));

        // consulta los datos de la comuna al microservicio de ubicacion
        ComunaResponse comuna = restTemplate.getForObject(
                UBICACION_MS_API + dto.getIdComuna(),
                ComunaResponse.class
        );

        // si no encuentra la comuna o no tiene nombre, lanza excepcion
        if (comuna == null || comuna.getNombre() == null) {
            throw new RecursoNoEncontradoException("comuna no encontrada con id: " + dto.getIdComuna());
        }

        // convierte el dto en entity y lo guarda en la base de datos
        ComunaCubiertaEntity entity = modelMapper.map(dto, ComunaCubiertaEntity.class);
        ComunaCubiertaEntity guardado = comunaCubiertaRepository.save(entity);

        // devuelve el dto correspondiente al registro guardado
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
    public ComunaCubiertaDTO obtenerPorId(int idCentro, int idComuna) {
        // crea el id compuesto a partir de los parametros
        ComunaCubiertaPKcompuesta id = new ComunaCubiertaPKcompuesta(idCentro, idComuna);

        // busca la comuna cubierta por id y lanza excepcion si no existe
        ComunaCubiertaEntity entity = comunaCubiertaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                    "comuna cubierta no encontrada para centro: " + idCentro + 
                    " y comuna: " + idComuna));

        // convierte la entidad a dto y lo devuelve
        return modelMapper.map(entity, ComunaCubiertaDTO.class);
    }

    @Override
    public void eliminarPorId(int idCentro, int idComuna) {
        // crea el id compuesto para buscar la comuna cubierta
        ComunaCubiertaPKcompuesta id = new ComunaCubiertaPKcompuesta(idCentro, idComuna);

        // verifica que exista antes de eliminarla
        if (!comunaCubiertaRepository.existsById(id)) {
            throw new RecursoNoEncontradoException(
                "comuna cubierta no encontrada para centro: " + idCentro + 
                " y comuna: " + idComuna);
        }

        // elimina la comuna cubierta por su id
        comunaCubiertaRepository.deleteById(id);
    }

       
    @Override
    public List<ComunaCubiertaDTO> obtenerPorIdCentro(int idCentro) {
        // busca todas las comunas asociadas a un centro especifico
        List<ComunaCubiertaEntity> entities = comunaCubiertaRepository.findByIdCentro(idCentro);
        
        // valida si no hay resultados
        if (entities.isEmpty()) {
            throw new RecursoNoEncontradoException("No se encontraron comunas para el centro: " + idCentro);
        }
        
        // convierte las entidades a DTOs
        return entities.stream()
                .map(entity -> modelMapper.map(entity, ComunaCubiertaDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ComunaCubiertaDTO> obtenerPorIdComuna(int idComuna) {
        // busca todas las ocurrencias de una comuna en diferentes centros
        List<ComunaCubiertaEntity> entities = comunaCubiertaRepository.findByIdComuna(idComuna);
        
        // valida si no hay resultados
        if (entities.isEmpty()) {
            throw new RecursoNoEncontradoException("No se encontraron comunas con ID: " + idComuna);
        }
        
        // convierte las entidades a DTOs
        return entities.stream()
                .map(entity -> modelMapper.map(entity, ComunaCubiertaDTO.class))
                .collect(Collectors.toList());
    }
}