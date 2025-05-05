package cl.fullstack.ubicacion_ms.service.impl;

import cl.fullstack.ubicacion_ms.dto.RegionDTO;
import cl.fullstack.ubicacion_ms.entity.RegionEntity;
import cl.fullstack.ubicacion_ms.exception.RecursoNoEncontradoException;
import cl.fullstack.ubicacion_ms.repository.RegionRepository;
import cl.fullstack.ubicacion_ms.service.IRegionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
        RegionEntity entity = modelMapper.map(regionDTO, RegionEntity.class);
        RegionEntity savedEntity = regionRepository.save(entity);
        return modelMapper.map(savedEntity, RegionDTO.class);
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
                .orElseThrow(() -> new RecursoNoEncontradoException("Region no encontrada con ID: " + idRegion));
        return modelMapper.map(entity, RegionDTO.class);
    }

    @Override
    @Transactional
    public void eliminarRegion(int idRegion) {
        if (!regionRepository.existsById(idRegion)) {
            throw new RecursoNoEncontradoException("Region no encontrada con ID: " + idRegion);
        }
        regionRepository.deleteById(idRegion);
    }
}
