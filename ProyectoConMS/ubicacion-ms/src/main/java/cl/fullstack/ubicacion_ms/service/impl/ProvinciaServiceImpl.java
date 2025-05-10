package cl.fullstack.ubicacion_ms.service.impl;

import cl.fullstack.ubicacion_ms.dto.ProvinciaDTO;

import cl.fullstack.ubicacion_ms.entity.ProvinciaEntity;
import cl.fullstack.ubicacion_ms.entity.RegionEntity;
import cl.fullstack.ubicacion_ms.exception.RecursoNoEncontradoException;
import cl.fullstack.ubicacion_ms.repository.ProvinciaRepository;
import cl.fullstack.ubicacion_ms.repository.RegionRepository;
import cl.fullstack.ubicacion_ms.service.IProvinciaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
        
        // Validar que la region exista
        RegionEntity regionEntity = regionRepository.findById(provinciaDTO.getRegion().getIdRegion())
                .orElseThrow(() -> new RecursoNoEncontradoException("La region asociada no existe"));

        ProvinciaEntity entity = modelMapper.map(provinciaDTO, ProvinciaEntity.class);
        entity.setRegion(regionEntity); // Asignar la entidad de region
        ProvinciaEntity savedEntity = provinciaRepository.save(entity);
        return modelMapper.map(savedEntity, ProvinciaDTO.class);
    }

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
                .orElseThrow(() -> new RecursoNoEncontradoException("Provincia no encontrada con ID: " + idProvincia));
        return modelMapper.map(entity, ProvinciaDTO.class);
    }

    @Override
    @Transactional
    public void eliminarProvincia(int idProvincia) {
        if (!provinciaRepository.existsById(idProvincia)) {
            throw new RecursoNoEncontradoException("Provincia no encontrada con ID: " + idProvincia);
        }
        provinciaRepository.deleteById(idProvincia);
    }
}