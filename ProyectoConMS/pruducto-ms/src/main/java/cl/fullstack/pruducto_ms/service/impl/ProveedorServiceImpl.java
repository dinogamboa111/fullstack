package cl.fullstack.pruducto_ms.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.fullstack.pruducto_ms.dto.ProveedorDTO;
import cl.fullstack.pruducto_ms.entity.ProveedorEntity;
import cl.fullstack.pruducto_ms.exception.RecursoNoEncontradoException;
import cl.fullstack.pruducto_ms.repository.ProveedorRepository;
import cl.fullstack.pruducto_ms.service.IProveedorService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProveedorServiceImpl implements IProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProveedorDTO createProveedor(ProveedorDTO dto) {
        ProveedorEntity proveedor = modelMapper.map(dto, ProveedorEntity.class);
        return modelMapper.map(proveedorRepository.save(proveedor), ProveedorDTO.class);
    }

    @Override
    public ProveedorDTO getProveedorById(Long id) {
        ProveedorEntity proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Proveedor no encontrado con ID: " + id));
        return modelMapper.map(proveedor, ProveedorDTO.class);
    }

    @Override
    public List<ProveedorDTO> getAllProveedores() {
        return proveedorRepository.findAll().stream()
                .map(proveedor -> modelMapper.map(proveedor, ProveedorDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProveedorDTO updateProveedor(Long id, ProveedorDTO dto) {
        proveedorRepository.findById(id)
          .orElseThrow(() -> new RecursoNoEncontradoException("Proveedor no encontrado con ID: " + id));
        dto.setId(id); // aseguramos que no cambie el ID
        ProveedorEntity actualizado = modelMapper.map(dto, ProveedorEntity.class);
        return modelMapper.map(proveedorRepository.save(actualizado), ProveedorDTO.class);
    }

    @Override
    public void deleteProveedor(Long id) {
        ProveedorEntity proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Proveedor no encontrado con ID: " + id));
        proveedorRepository.delete(proveedor);
    }
}
