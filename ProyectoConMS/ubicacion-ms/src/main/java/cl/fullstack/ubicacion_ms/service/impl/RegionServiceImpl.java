package cl.fullstack.ubicacion_ms.service.impl;

import cl.fullstack.ubicacion_ms.dto.RegionDTO;
import cl.fullstack.ubicacion_ms.entity.RegionEntity;
import cl.fullstack.ubicacion_ms.exception.NotFoundException;
import cl.fullstack.ubicacion_ms.exception.RecursoDuplicadoException;
import cl.fullstack.ubicacion_ms.exception.IntegridadDatosException;
import cl.fullstack.ubicacion_ms.repository.RegionRepository;
import cl.fullstack.ubicacion_ms.service.IRegionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegionServiceImpl implements IRegionService {

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private ModelMapper modelMapper;



    @Override
@Transactional
public RegionDTO crearRegion(RegionDTO regionDTO) {
    if (regionDTO.getNombre() == null || regionDTO.getNombre().trim().isEmpty()) {
        throw new IllegalArgumentException("El nombre de la region no puede estar vacio");
    }

    // Validar por nombre ignorando mayusculas/minusculas
    if (regionRepository.findByNombreIgnoreCase(regionDTO.getNombre().trim()).isPresent()) {
        throw new RecursoDuplicadoException("Ya existe una region con el nombre: " + regionDTO.getNombre());
    }

    // Validar por ID
    if (regionDTO.getIdRegion() != 0 && regionRepository.existsById(regionDTO.getIdRegion())) {
        throw new RecursoDuplicadoException("La region con ID " + regionDTO.getIdRegion() + " ya existe");
    }

    RegionEntity entity = modelMapper.map(regionDTO, RegionEntity.class);
    RegionEntity savedEntity = regionRepository.save(entity);
    return modelMapper.map(savedEntity, RegionDTO.class);
}

  
    @Override
    @Transactional
    public RegionDTO actualizarRegion(int idRegion, RegionDTO regionDTO) {
        if (regionDTO.getNombre() == null || regionDTO.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la region no puede estar vacio");
        }
        RegionEntity entity = regionRepository.findById(idRegion)
                .orElseThrow(() -> new NotFoundException("Region no encontrada con ID: " + idRegion));
        entity.setNombre(regionDTO.getNombre());
        RegionEntity updatedEntity = regionRepository.save(entity);
        return modelMapper.map(updatedEntity, RegionDTO.class);
    }

    @Override
    public List<RegionDTO> obtenerRegiones() {
        return regionRepository.findAll().stream()
                .map(entity -> modelMapper.map(entity, RegionDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public RegionDTO obtenerRegionPorId(int idRegion) {
        RegionEntity entity = regionRepository.findById(idRegion)
                .orElseThrow(() -> new NotFoundException("Region no encontrada con ID: " + idRegion));
        return modelMapper.map(entity, RegionDTO.class);
    }

    @Override
    @Transactional
    public void eliminarRegion(int idRegion) {
        RegionEntity region = regionRepository.findById(idRegion)
                .orElseThrow(() -> new NotFoundException("Region no encontrada con ID: " + idRegion));
        
        try {
            regionRepository.delete(region);
        } catch (DataIntegrityViolationException e) {
            throw new IntegridadDatosException("No se puede eliminar la region porque tiene provincias asociadas");
        }
    }
}


