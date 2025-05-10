package cl.fullstack.pruducto_ms.service;

import java.util.List;

import cl.fullstack.pruducto_ms.dto.ProveedorDTO;

public interface IProveedorService {
    List<ProveedorDTO> getAllProveedores();
    ProveedorDTO getProveedorById(int id);
    ProveedorDTO createProveedor(ProveedorDTO proveedorDTO);
    ProveedorDTO updateProveedor(int id, ProveedorDTO proveedorDTO);
    void deleteProveedor(int id);
}