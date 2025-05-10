package cl.fullstack.ubicacion_ms.service.impl;

import cl.fullstack.ubicacion_ms.dto.ComunaDTO;

import cl.fullstack.ubicacion_ms.entity.ComunaEntity;
import cl.fullstack.ubicacion_ms.entity.ProvinciaEntity;
import cl.fullstack.ubicacion_ms.exception.RecursoNoEncontradoException;
import cl.fullstack.ubicacion_ms.repository.ComunaRepository;
import cl.fullstack.ubicacion_ms.repository.ProvinciaRepository;
import cl.fullstack.ubicacion_ms.service.IComunaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComunaServiceImpl implements IComunaService {

    @Autowired
    private ComunaRepository comunaRepository;
    @Autowired
    private ProvinciaRepository provinciaRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public ComunaDTO crearComuna(ComunaDTO comunaDTO) {
        if (comunaDTO.getNombre() == null || comunaDTO.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la comuna no puede estar vacio");
        }
        
        // Validar que la provincia exista
        ProvinciaEntity provinciaEntity = provinciaRepository.findById(comunaDTO.getProvincia().getIdProvincia())
                .orElseThrow(() -> new RecursoNoEncontradoException("La provincia asociada no existe"));

        ComunaEntity entity = modelMapper.map(comunaDTO, ComunaEntity.class);
        entity.setProvincia(provinciaEntity); // Asignar la entidad de provincia
        ComunaEntity savedEntity = comunaRepository.save(entity);
        return modelMapper.map(savedEntity, ComunaDTO.class);
    }

    @Override
    public List<ComunaDTO> obtenerComunas() {
        return comunaRepository.findAll().stream()
                .map(entity -> modelMapper.map(entity, ComunaDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ComunaDTO> obtenerComunasPorProvincia(int idProvincia) {
        return comunaRepository.findByProvinciaIdProvincia(idProvincia).stream()
                .map(entity -> modelMapper.map(entity, ComunaDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ComunaDTO obtenerComunaPorId(int idComuna) {
        ComunaEntity entity = comunaRepository.findById(idComuna)
                .orElseThrow(() -> new RecursoNoEncontradoException("Comuna no encontrada con ID: " + idComuna));
        return modelMapper.map(entity, ComunaDTO.class);
    }

    @Override
    @Transactional
    public void eliminarComuna(int idComuna) {
        if (!comunaRepository.existsById(idComuna)) {
            throw new RecursoNoEncontradoException("Comuna no encontrada con ID: " + idComuna);
        }
        comunaRepository.deleteById(idComuna);
    }
}