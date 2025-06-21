package cl.fullstack.centro_distribucion_ms.service.impl;

import cl.fullstack.centro_distribucion_ms.dto.CentroDistribucionDTO;
import cl.fullstack.centro_distribucion_ms.entity.CentroDistribucionEntity;
import cl.fullstack.centro_distribucion_ms.exception.RecursoNoEncontradoException;
import cl.fullstack.centro_distribucion_ms.repository.CentroDistribucionRepository;
import cl.fullstack.centro_distribucion_ms.response.ComunaResponse;
import cl.fullstack.centro_distribucion_ms.service.ICentroDistribucionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service // marca la clase como un servicio manejado por spring
public class CentroDistribucionServiceImpl implements ICentroDistribucionService {

    @Autowired
    private CentroDistribucionRepository centroDistribucionRepository; // repositorio para acceder a los datos del
                                                                       // centro

    @Autowired
    private ModelMapper modelMapper; // para convertir entre entity y dto

    @Autowired
    private RestTemplate restTemplate; // para hacer peticiones http a otros microservicios

    private static final String UBICACION_MS_API = "http://ubicacion-ms/api/comunas/"; // url del microservicio de
                                                                                       // ubicacion

    @Override
    @Transactional // asegura que la operacion sea parte de una transaccion en la base de datos
    public CentroDistribucionDTO crearCentroDistribucion(CentroDistribucionDTO dto) {
        // obtiene los datos de la comuna desde el microservicio ubicacion-ms
        ComunaResponse comuna = restTemplate.getForObject(
                UBICACION_MS_API + dto.getIdComuna(),
                ComunaResponse.class);

        // si la comuna no existe lanza una excepcion personalizada
        if (comuna == null || comuna.getNombre() == null) {
            throw new RecursoNoEncontradoException("comuna no encontrada con id: " + dto.getIdComuna());
        }

        // completa los campos del dto con los datos obtenidos desde el microservicio
        dto.setNombreComuna(comuna.getNombre());
        dto.setNombreProvincia(comuna.getProvincia().getNombre());
        dto.setNombreRegion(comuna.getProvincia().getRegion().getNombre());

        // convierte el dto a entity para guardar en la base de datos
        CentroDistribucionEntity entity = modelMapper.map(dto, CentroDistribucionEntity.class);

        // guarda la entity y obtiene el resultado
        CentroDistribucionEntity guardado = centroDistribucionRepository.save(entity);

        // convierte la entity guardada a dto y la retorna
        return modelMapper.map(guardado, CentroDistribucionDTO.class);
    }

    @Override
    public List<CentroDistribucionDTO> obtenerTodosLosCentros() {
        // obtiene todas las entidades desde la base y las convierte a lista de dto
        return centroDistribucionRepository.findAll().stream()
                .map(entity -> modelMapper.map(entity, CentroDistribucionDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public CentroDistribucionDTO obtenerCentroDistribucionPorId(int idCentro) {
        // busca un centro por id, si no lo encuentra lanza excepcion
        CentroDistribucionEntity entity = centroDistribucionRepository.findById(idCentro)
                .orElseThrow(() -> new RecursoNoEncontradoException("centro no encontrado con id: " + idCentro));

        // convierte la entidad a dto y la retorna
        return modelMapper.map(entity, CentroDistribucionDTO.class);
    }

    @Override
    @Transactional // asegura que la eliminacion sea parte de una transaccion
    public void eliminarCentroDistribucion(int idCentro) {
        // valida que exista el centro antes de intentar eliminarlo
        if (!centroDistribucionRepository.existsById(idCentro)) {
            throw new RecursoNoEncontradoException("centro no encontrado con id: " + idCentro);
        }

        // elimina el centro por id
        centroDistribucionRepository.deleteById(idCentro);
    }

    @Override
    public Optional<CentroDistribucionEntity> findByComunaCubierta(int idComuna) {
        return centroDistribucionRepository.findByComunaCubierta(idComuna);
    }
}