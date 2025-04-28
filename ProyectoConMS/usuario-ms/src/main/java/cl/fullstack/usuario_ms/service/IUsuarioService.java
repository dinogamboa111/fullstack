package cl.fullstack.usuario_ms.service;

import cl.fullstack.usuario_ms.dto.UsuarioDTO;

import java.util.List;

public interface IUsuarioService {
    List<UsuarioDTO> getAllUsuarios();
    UsuarioDTO getUsuarioById(Long id);
    UsuarioDTO createUsuario(UsuarioDTO usuarioDTO);
    UsuarioDTO updateUsuario(Long id, UsuarioDTO usuarioDTO);
    void deleteUsuario(Long id);
}