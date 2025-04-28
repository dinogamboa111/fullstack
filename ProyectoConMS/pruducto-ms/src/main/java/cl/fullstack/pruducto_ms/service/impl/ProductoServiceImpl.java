package cl.fullstack.pruducto_ms.service.impl;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.fullstack.pruducto_ms.dto.ProductoDTO;
import cl.fullstack.pruducto_ms.entity.ProductoEntity;
import cl.fullstack.pruducto_ms.exception.RecursoNoEncontradoException;
import cl.fullstack.pruducto_ms.repository.ProductoRepository;
import cl.fullstack.pruducto_ms.service.IProductoService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoServiceImpl implements IProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProductoDTO createProducto(ProductoDTO dto) {
        ProductoEntity producto = modelMapper.map(dto, ProductoEntity.class);
        return modelMapper.map(productoRepository.save(producto), ProductoDTO.class);
    }

    @Override
    public ProductoDTO getProductoById(Long id) {
        ProductoEntity producto = productoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado con ID: " + id));
        return modelMapper.map(producto, ProductoDTO.class);
    }

    @Override
    public List<ProductoDTO> getAllProductos() {
        return productoRepository.findAll().stream()
                .map(producto -> modelMapper.map(producto, ProductoDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProductoDTO updateProducto(Long id, ProductoDTO dto) {
        productoRepository.findById(id)
          .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado con ID: " + id));
        dto.setId(id); // aseguramos que no cambie el ID
        ProductoEntity actualizado = modelMapper.map(dto, ProductoEntity.class);
        return modelMapper.map(productoRepository.save(actualizado), ProductoDTO.class);
    }

    @Override
    public void deleteProducto(Long id) {
        ProductoEntity producto = productoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado con ID: " + id));
        productoRepository.delete(producto);
    }
}
