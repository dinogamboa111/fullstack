package cl.fullstack.pruducto_ms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.fullstack.pruducto_ms.dto.ProveedorDTO;
import cl.fullstack.pruducto_ms.entity.ProveedorEntity;
import cl.fullstack.pruducto_ms.repository.ProveedorRepository;
import cl.fullstack.pruducto_ms.service.IProveedorService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProveedorServiceImpl implements IProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Override
    public List<ProveedorDTO> getAllProveedores() {
        List<ProveedorEntity> proveedores = proveedorRepository.findAll();
        return proveedores.stream().map(proveedor -> {
            ProveedorDTO dto = new ProveedorDTO();
            dto.setId(proveedor.getId());
            dto.setNombre(proveedor.getNombre());
            dto.setTelefono(proveedor.getTelefono());
            dto.setDireccion(proveedor.getDireccion());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public ProveedorDTO getProveedorById(Long id) {
        ProveedorEntity proveedor = proveedorRepository.findById(id).orElse(null);
        if (proveedor == null) return null;
        ProveedorDTO dto = new ProveedorDTO();
        dto.setId(proveedor.getId());
        dto.setNombre(proveedor.getNombre());
        dto.setTelefono(proveedor.getTelefono());
        dto.setDireccion(proveedor.getDireccion());
        return dto;
    }

    @Override
    public ProveedorDTO createProveedor(ProveedorDTO proveedorDTO) {
        ProveedorEntity proveedor = new ProveedorEntity();
        proveedor.setNombre(proveedorDTO.getNombre());
        proveedor.setTelefono(proveedorDTO.getTelefono());
        proveedor.setDireccion(proveedorDTO.getDireccion());
        proveedorRepository.save(proveedor);
        proveedorDTO.setId(proveedor.getId());
        return proveedorDTO;
    }

    @Override
    public ProveedorDTO updateProveedor(Long id, ProveedorDTO proveedorDTO) {
        ProveedorEntity proveedor = proveedorRepository.findById(id).orElse(null);
        if (proveedor != null) {
            proveedor.setNombre(proveedorDTO.getNombre());
            proveedor.setTelefono(proveedorDTO.getTelefono());
            proveedor.setDireccion(proveedorDTO.getDireccion());
            proveedorRepository.save(proveedor);
            proveedorDTO.setId(proveedor.getId());
        }
        return proveedorDTO;
    }

    @Override
    public void deleteProveedor(Long id) {
        proveedorRepository.deleteById(id);
    }
}
