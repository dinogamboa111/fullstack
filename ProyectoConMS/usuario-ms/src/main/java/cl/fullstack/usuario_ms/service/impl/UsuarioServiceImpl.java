package cl.fullstack.usuario_ms.service.impl;

import cl.fullstack.usuario_ms.Client.CentroDistribucionClient;
import cl.fullstack.usuario_ms.dto.UsuarioDTO;
import cl.fullstack.usuario_ms.entity.UsuarioEntity;
import cl.fullstack.usuario_ms.exception.RecursoNoEncontradoException;
import cl.fullstack.usuario_ms.repository.UsuarioRepository;
import cl.fullstack.usuario_ms.service.IUsuarioService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CentroDistribucionClient centroDistribucionClient;

    @Override
    public UsuarioDTO createUsuario(UsuarioDTO usuarioDTO) {
        // Mapea DTO a Entity
        UsuarioEntity usuario = modelMapper.map(usuarioDTO, UsuarioEntity.class);

        // Si el usuario es despachador (rol id == 1)
        if (usuario.getRol() != null && usuario.getRol().getId() == 1) {
            centroDistribucionClient.obtenerCentroPorComuna(usuarioDTO.getIdComuna())
                    .ifPresent(centro -> usuario.setIdCentro(centro.getIdCentro()));
        }

        // Guarda en BD
        UsuarioEntity saved = usuarioRepository.save(usuario);

        // Recarga usuario con rol para que venga completo (nombre y descripción)
        UsuarioEntity usuarioConRol = usuarioRepository.findByIdWithRol(saved.getId())
                .orElse(saved); // fallback si no lo encuentra

        // Retorna el DTO mapeado con rol completo
        return modelMapper.map(usuarioConRol, UsuarioDTO.class);
    }

    @Override
    public Optional<UsuarioDTO> findDespachadorByComuna(int idComuna) {
        Optional<UsuarioEntity> usuarioOpt = usuarioRepository.findDespachadorByRolIdAndComuna(null, idComuna);
        return usuarioOpt.map(usuario -> modelMapper.map(usuario, UsuarioDTO.class));
    }

    @Override
    public UsuarioDTO buscarDespachadorPorCentro(int idCentro) {
        // Rol 1 = Despachador (ajusta si usas otro ID)
        Optional<UsuarioEntity> usuarioOpt = usuarioRepository.findFirstByIdCentroAndRolId(idCentro, 1);

        if (usuarioOpt.isEmpty()) {
            throw new RecursoNoEncontradoException("No se encontró despachador para el centro " + idCentro);
        }

        return modelMapper.map(usuarioOpt.get(), UsuarioDTO.class);
    }

    // @Override
    // public UsuarioDTO createUsuario(UsuarioDTO usuarioDTO) {
    // // Mapea DTO a Entity
    // UsuarioEntity usuario = modelMapper.map(usuarioDTO, UsuarioEntity.class);

    // // Si el usuario es despachador (rol id == 1)
    // if (usuario.getRol() != null && usuario.getRol().getId() == 1) {
    // // Obtiene el centro de distribución según la comuna
    // centroDistribucionClient.obtenerCentroPorComuna(usuarioDTO.getIdComuna())
    // .ifPresent(centro -> usuario.setIdCentro(centro.getIdCentro()));
    // }

    // // Guarda en BD
    // UsuarioEntity saved = usuarioRepository.save(usuario);

    // // Retorna el DTO mapeado de la entidad guardada
    // return modelMapper.map(saved, UsuarioDTO.class);
    // }

    // @Override
    // public UsuarioDTO createUsuario(UsuarioDTO usuarioDTO) {
    // UsuarioEntity usuario = modelMapper.map(usuarioDTO, UsuarioEntity.class);
    // return modelMapper.map(usuarioRepository.save(usuario), UsuarioDTO.class);
    // }

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
