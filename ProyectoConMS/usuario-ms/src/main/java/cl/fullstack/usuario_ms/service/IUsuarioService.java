package cl.fullstack.usuario_ms.service;

import cl.fullstack.usuario_ms.dto.UsuarioDTO;


import java.util.List;


public interface IUsuarioService {
    List<UsuarioDTO> getAllUsuarios();

    UsuarioDTO getUsuarioById(int id);

    UsuarioDTO createUsuario(UsuarioDTO usuarioDTO);

    UsuarioDTO updateUsuario(int id, UsuarioDTO usuarioDTO);

    void deleteUsuario(int id);

    UsuarioDTO findPrimerUsuarioPorCentro(Integer idCentro);

    List<UsuarioDTO> buscarDespachadoresPorCentro(int idCentro);


}