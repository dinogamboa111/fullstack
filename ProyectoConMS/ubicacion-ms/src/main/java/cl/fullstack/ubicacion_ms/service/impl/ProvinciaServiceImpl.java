package cl.fullstack.ubicacion_ms.service.impl;

import cl.fullstack.ubicacion_ms.dto.ProvinciaDTO;
import cl.fullstack.ubicacion_ms.entity.ProvinciaEntity;
import cl.fullstack.ubicacion_ms.entity.RegionEntity;
import cl.fullstack.ubicacion_ms.exception.NotFoundException;
import cl.fullstack.ubicacion_ms.exception.RecursoDuplicadoException;
import cl.fullstack.ubicacion_ms.exception.IntegridadDatosException;
import cl.fullstack.ubicacion_ms.repository.ProvinciaRepository;
import cl.fullstack.ubicacion_ms.repository.RegionRepository;
import cl.fullstack.ubicacion_ms.service.IProvinciaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProvinciaServiceImpl implements IProvinciaService {

    @Autowired
    private ProvinciaRepository provinciaRepository;
    
    @Autowired
    private RegionRepository regionRepository;
    
    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public ProvinciaDTO crearProvincia(ProvinciaDTO provinciaDTO) {
        if (provinciaDTO.getNombre() == null || provinciaDTO.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la provincia no puede estar vacio");
        }

        if (provinciaDTO.getIdProvincia() != 0 && provinciaRepository.existsById(provinciaDTO.getIdProvincia())) {
            throw new RecursoDuplicadoException("La provincia con ID " + provinciaDTO.getIdProvincia() + " ya existe");
        }

        // Validar que la region asociada exista
        int idRegion = provinciaDTO.getRegion().getIdRegion();
        RegionEntity regionEntity = regionRepository.findById(idRegion)
                .orElseThrow(() -> new NotFoundException("La region asociada no existe"));

        // Validar que no exista otra provincia con el mismo nombre en la misma region (case-insensitive)
        String nombreNormalizado = provinciaDTO.getNombre().trim();
        if (provinciaRepository.findByNombreIgnoreCaseAndRegionIdRegion(nombreNormalizado, idRegion).isPresent()) {
            throw new RecursoDuplicadoException("Ya existe una provincia con el nombre '" + nombreNormalizado + "' en la region seleccionada");
        }

        ProvinciaEntity entity = modelMapper.map(provinciaDTO, ProvinciaEntity.class);
        entity.setRegion(regionEntity);
        ProvinciaEntity savedEntity = provinciaRepository.save(entity);

        return modelMapper.map(savedEntity, ProvinciaDTO.class);
    }

    // @Override
    // @Transactional
    // public ProvinciaDTO crearProvincia(ProvinciaDTO provinciaDTO) {
    //     if (provinciaDTO.getNombre() == null || provinciaDTO.getNombre().trim().isEmpty()) {
    //         throw new IllegalArgumentException("El nombre de la provincia no puede estar vacio");
    //     }
        
    //     // Validar que el ID no exista
    //     if (provinciaDTO.getIdProvincia() != 0 && provinciaRepository.existsById(provinciaDTO.getIdProvincia())) {
    //         throw new RecursoDuplicadoException("La provincia con ID " + provinciaDTO.getIdProvincia() + " ya existe");
    //     }
        
    //     // Validar que la region exista
    //     RegionEntity regionEntity = regionRepository.findById(provinciaDTO.getRegion().getIdRegion())
    //             .orElseThrow(() -> new NotFoundException("La region asociada no existe"));
        
    //     ProvinciaEntity entity = modelMapper.map(provinciaDTO, ProvinciaEntity.class);
    //     entity.setRegion(regionEntity);
    //     ProvinciaEntity savedEntity = provinciaRepository.save(entity);
    //     return modelMapper.map(savedEntity, ProvinciaDTO.class);
    // }

    @Override
    public List<ProvinciaDTO> obtenerProvincias() {
        return provinciaRepository.findAll().stream()
                .map(entity -> modelMapper.map(entity, ProvinciaDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProvinciaDTO> obtenerProvinciasPorRegion(int idRegion) {
        return provinciaRepository.findByRegionIdRegion(idRegion).stream()
                .map(entity -> modelMapper.map(entity, ProvinciaDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProvinciaDTO obtenerProvinciaPorId(int idProvincia) {
        ProvinciaEntity entity = provinciaRepository.findById(idProvincia)
                .orElseThrow(() -> new NotFoundException("Provincia no encontrada con ID: " + idProvincia));
        return modelMapper.map(entity, ProvinciaDTO.class);
    }

    @Override
    @Transactional
    public void eliminarProvincia(int idProvincia) {
        ProvinciaEntity provincia = provinciaRepository.findById(idProvincia)
                .orElseThrow(() -> new NotFoundException("Provincia no encontrada con ID: " + idProvincia));
        
        try {
            provinciaRepository.delete(provincia);
        } catch (DataIntegrityViolationException e) {
            throw new IntegridadDatosException("No se puede eliminar la provincia porque tiene comunas asociadas");
        }
    }

    @Override
    @Transactional
    public ProvinciaDTO actualizarProvincia(int idProvincia, ProvinciaDTO provinciaDTO) {
        if (provinciaDTO.getNombre() == null || provinciaDTO.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la provincia no puede estar vacio");
        }
        
        // Validar que la provincia exista
        ProvinciaEntity entity = provinciaRepository.findById(idProvincia)
                .orElseThrow(() -> new NotFoundException("Provincia no encontrada con ID: " + idProvincia));
        
        // Validar que la region exista
        RegionEntity regionEntity = regionRepository.findById(provinciaDTO.getRegion().getIdRegion())
                .orElseThrow(() -> new NotFoundException("La region asociada no existe"));
        
        entity.setNombre(provinciaDTO.getNombre());
        entity.setRegion(regionEntity);
        
        ProvinciaEntity updatedEntity = provinciaRepository.save(entity);
        return modelMapper.map(updatedEntity, ProvinciaDTO.class);
    }
}