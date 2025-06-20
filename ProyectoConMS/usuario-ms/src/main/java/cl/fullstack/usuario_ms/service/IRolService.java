package cl.fullstack.usuario_ms.service;

import cl.fullstack.usuario_ms.dto.RolDTO;

import java.util.List;

public interface IRolService {
    RolDTO crearRol(RolDTO rolDTO);
    List<RolDTO> listarRoles();
    RolDTO obtenerPorId(Long id);
    RolDTO actualizarRol(Long id, RolDTO rolDTO);
    void eliminarRol(Long id);
}