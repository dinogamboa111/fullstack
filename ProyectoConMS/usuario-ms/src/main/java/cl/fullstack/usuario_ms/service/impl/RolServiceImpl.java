package cl.fullstack.usuario_ms.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.fullstack.usuario_ms.dto.RolDTO;
import cl.fullstack.usuario_ms.entity.RolEntity;
import cl.fullstack.usuario_ms.repository.RolRepository;
import cl.fullstack.usuario_ms.service.IRolService;


import org.modelmapper.ModelMapper;

@Service
public class RolServiceImpl implements IRolService {

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public RolDTO crearRol(RolDTO rolDTO) {
        RolEntity rol = modelMapper.map(rolDTO, RolEntity.class);
        RolEntity guardado = rolRepository.save(rol);
        return modelMapper.map(guardado, RolDTO.class);
    }

    @Override
    public List<RolDTO> listarRoles() {
        return rolRepository.findAll().stream()
            .map(rol -> modelMapper.map(rol, RolDTO.class))
            .collect(Collectors.toList());
    }

    @Override
    public RolDTO obtenerPorId(Long id) {
        RolEntity rol = rolRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        return modelMapper.map(rol, RolDTO.class);
    }

    @Override
    public RolDTO actualizarRol(Long id, RolDTO rolDTO) {
        RolEntity rolExistente = rolRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        rolExistente.setNombre(rolDTO.getNombre());
        rolExistente.setDescripcion(rolDTO.getDescripcion());

        return modelMapper.map(rolRepository.save(rolExistente), RolDTO.class);
    }

    @Override
    public void eliminarRol(Long id) {
        rolRepository.deleteById(id);
    }
}