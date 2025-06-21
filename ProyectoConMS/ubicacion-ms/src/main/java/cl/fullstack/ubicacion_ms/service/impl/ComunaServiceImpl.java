package cl.fullstack.ubicacion_ms.service.impl;

import cl.fullstack.ubicacion_ms.dto.ComunaDTO;
import cl.fullstack.ubicacion_ms.entity.ComunaEntity;
import cl.fullstack.ubicacion_ms.entity.ProvinciaEntity;
import cl.fullstack.ubicacion_ms.exception.NotFoundException;
import cl.fullstack.ubicacion_ms.exception.RecursoDuplicadoException;
import cl.fullstack.ubicacion_ms.exception.IntegridadDatosException;
import cl.fullstack.ubicacion_ms.repository.ComunaRepository;
import cl.fullstack.ubicacion_ms.repository.ProvinciaRepository;
import cl.fullstack.ubicacion_ms.service.IComunaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
            throw new IllegalArgumentException("El nombre de la comuna no puede estar vacío");
        }

        if (comunaDTO.getIdComuna() != 0 && comunaRepository.existsById(comunaDTO.getIdComuna())) {
            throw new RecursoDuplicadoException("La comuna con ID " + comunaDTO.getIdComuna() + " ya existe");
        }

        // Validar que la provincia exista
        ProvinciaEntity provinciaEntity = provinciaRepository.findById(comunaDTO.getProvincia().getIdProvincia())
                .orElseThrow(() -> new NotFoundException("La provincia asociada no existe"));

        // Validar que no exista otra comuna con el mismo nombre en la misma provincia (case-insensitive)
        String nombreNormalizado = comunaDTO.getNombre().trim();
        if (comunaRepository.findByNombreIgnoreCaseAndProvincia_IdProvincia(nombreNormalizado, provinciaEntity.getIdProvincia()).isPresent()) {
            throw new RecursoDuplicadoException("Ya existe una comuna con el nombre '" + nombreNormalizado + "' en la provincia seleccionada");
        }

        ComunaEntity entity = modelMapper.map(comunaDTO, ComunaEntity.class);
        entity.setProvincia(provinciaEntity);
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
        return comunaRepository.findByProvincia_IdProvincia(idProvincia).stream()
                .map(entity -> modelMapper.map(entity, ComunaDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ComunaDTO obtenerComunaPorId(int idComuna) {
        ComunaEntity entity = comunaRepository.findById(idComuna)
                .orElseThrow(() -> new NotFoundException("Comuna no encontrada con ID: " + idComuna));
        return modelMapper.map(entity, ComunaDTO.class);
    }

    @Override
    @Transactional
    public void eliminarComuna(int idComuna) {
        ComunaEntity comuna = comunaRepository.findById(idComuna)
                .orElseThrow(() -> new NotFoundException("Comuna no encontrada con ID: " + idComuna));

        try {
            comunaRepository.delete(comuna);
        } catch (DataIntegrityViolationException e) {
            throw new IntegridadDatosException("No se puede eliminar la comuna porque tiene registros asociados");
        }
    }

    @Override
    @Transactional
    public ComunaDTO actualizarComuna(int idComuna, ComunaDTO comunaDTO) {
        if (comunaDTO.getNombre() == null || comunaDTO.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la comuna no puede estar vacío");
        }

        ComunaEntity entity = comunaRepository.findById(idComuna)
                .orElseThrow(() -> new NotFoundException("Comuna no encontrada con ID: " + idComuna));

        ProvinciaEntity provinciaEntity = provinciaRepository.findById(comunaDTO.getProvincia().getIdProvincia())
                .orElseThrow(() -> new NotFoundException("La provincia asociada no existe"));

        entity.setNombre(comunaDTO.getNombre());
        entity.setProvincia(provinciaEntity);

        ComunaEntity updatedEntity = comunaRepository.save(entity);
        return modelMapper.map(updatedEntity, ComunaDTO.class);
    }
}
