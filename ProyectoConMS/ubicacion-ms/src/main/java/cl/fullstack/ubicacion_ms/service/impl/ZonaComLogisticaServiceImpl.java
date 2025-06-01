package cl.fullstack.ubicacion_ms.service.impl;

import cl.fullstack.ubicacion_ms.dto.ZonaComLogisticaDTO;
import cl.fullstack.ubicacion_ms.entity.ComunaEntity;
import cl.fullstack.ubicacion_ms.entity.ZonaComLogisticaEntity;
import cl.fullstack.ubicacion_ms.exception.RecursoNoEncontradoException;
import cl.fullstack.ubicacion_ms.repository.ZonaComLogisticaRepository;
import cl.fullstack.ubicacion_ms.service.IZonaComLogisticaService;
import cl.fullstack.ubicacion_ms.repository.ComunaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ZonaComLogisticaServiceImpl implements IZonaComLogisticaService {

    @Autowired
    private ZonaComLogisticaRepository zonacomlogisticaRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ComunaRepository comunaRepository;

    @Override
    @Transactional
    public ZonaComLogisticaDTO crearZonaComLogistica(ZonaComLogisticaDTO zonacomlogisticaDTO) {
            if (zonacomlogisticaDTO.getNombreZona() == null || zonacomlogisticaDTO.getNombreZona().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la zona no puede estar vacio");
        }

        // Obtener comuna por ID
    ComunaEntity comuna = comunaRepository.findById(zonacomlogisticaDTO.getIdComuna())
            .orElseThrow(() -> new RecursoNoEncontradoException("No existe la comuna con ID: " + zonacomlogisticaDTO.getIdComuna()));



    // Inyectar nombre de la comuna al DTO
    zonacomlogisticaDTO.setNombreComuna(comuna.getNombre());

    // Guardar normalmente
    ZonaComLogisticaEntity entity = modelMapper.map(zonacomlogisticaDTO, ZonaComLogisticaEntity.class);
    ZonaComLogisticaEntity savedEntity = zonacomlogisticaRepository.save(entity);

    return modelMapper.map(savedEntity, ZonaComLogisticaDTO.class);
    }



    @Override
    public List<ZonaComLogisticaDTO> obtenerZonaComLogistica() {
        return zonacomlogisticaRepository.findAll().stream()
                .map(entity -> modelMapper.map(entity, ZonaComLogisticaDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ZonaComLogisticaDTO obtenerZonaComLogisticaPorId(int idZonaComLogistica) {
        ZonaComLogisticaEntity entity = zonacomlogisticaRepository.findById(idZonaComLogistica)
                .orElseThrow(() -> new RecursoNoEncontradoException("Zona  no encontrada con ID: " + idZonaComLogistica));
        return modelMapper.map(entity, ZonaComLogisticaDTO.class);
    }

    @Override
    @Transactional
    public void eliminarZonaComLogistica(int idZonaComLogistica) {
        if (!zonacomlogisticaRepository.existsById(idZonaComLogistica)) {
            throw new RecursoNoEncontradoException("Zona  no encontrada con ID: " + idZonaComLogistica);
        }
        zonacomlogisticaRepository.deleteById(idZonaComLogistica);
    }
}
