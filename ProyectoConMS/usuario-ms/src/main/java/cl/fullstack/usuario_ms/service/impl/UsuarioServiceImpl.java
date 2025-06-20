package cl.fullstack.usuario_ms.service.impl;

import cl.fullstack.usuario_ms.dto.UsuarioDTO;
import cl.fullstack.usuario_ms.entity.UsuarioEntity;
import cl.fullstack.usuario_ms.exception.RecursoNoEncontradoException;
import cl.fullstack.usuario_ms.repository.UsuarioRepository;
import cl.fullstack.usuario_ms.service.IUsuarioService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<UsuarioDTO> getAllUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(usuario -> modelMapper.map(usuario, UsuarioDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public UsuarioDTO getUsuarioById(int id) {
        UsuarioEntity usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con ID: " + id));
        return modelMapper.map(usuario, UsuarioDTO.class);
    }

    @Override
    public UsuarioDTO createUsuario(UsuarioDTO usuarioDTO) {
        UsuarioEntity usuario = modelMapper.map(usuarioDTO, UsuarioEntity.class);
        return modelMapper.map(usuarioRepository.save(usuario), UsuarioDTO.class);
    }

    @Override
    public UsuarioDTO updateUsuario(int id, UsuarioDTO usuarioDTO) {
        usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con ID: " + id));
        usuarioDTO.setId(id); // aseguramos que no cambie el ID
        UsuarioEntity actualizado = modelMapper.map(usuarioDTO, UsuarioEntity.class);
        return modelMapper.map(usuarioRepository.save(actualizado), UsuarioDTO.class);
    }

    @Override
    public void deleteUsuario(int id) {
        UsuarioEntity usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con ID: " + id));
        usuarioRepository.delete(usuario);
    }
}

