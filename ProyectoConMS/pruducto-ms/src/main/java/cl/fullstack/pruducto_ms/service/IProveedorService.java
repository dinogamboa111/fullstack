package cl.fullstack.pruducto_ms.service;

import java.util.List;

import cl.fullstack.pruducto_ms.dto.ProveedorDTO;

public interface IProveedorService {
    List<ProveedorDTO> getAllProveedores();
    ProveedorDTO getProveedorById(Long id);
    ProveedorDTO createProveedor(ProveedorDTO proveedorDTO);
    ProveedorDTO updateProveedor(Long id, ProveedorDTO proveedorDTO);
    void deleteProveedor(Long id);
}