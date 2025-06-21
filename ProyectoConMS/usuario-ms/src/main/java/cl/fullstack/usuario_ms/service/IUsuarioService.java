package cl.fullstack.usuario_ms.service;

import cl.fullstack.usuario_ms.dto.UsuarioDTO;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService {
    List<UsuarioDTO> getAllUsuarios();
    UsuarioDTO getUsuarioById(int id);
    UsuarioDTO createUsuario(UsuarioDTO usuarioDTO);
    UsuarioDTO updateUsuario(int id, UsuarioDTO usuarioDTO);
    void deleteUsuario(int id);
    public Optional<UsuarioDTO> findDespachadorByComuna(int idComuna);
    UsuarioDTO buscarDespachadorPorCentro(int idCentro);
}