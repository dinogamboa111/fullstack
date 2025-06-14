package cl.fullstack.pruducto_ms.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import cl.fullstack.pruducto_ms.client.ClienteClient;
import cl.fullstack.pruducto_ms.dto.ClienteDTO;
import cl.fullstack.pruducto_ms.dto.ProductoDTO;
import cl.fullstack.pruducto_ms.entity.CategoriaEntity;
import cl.fullstack.pruducto_ms.entity.ProductoEntity;
import cl.fullstack.pruducto_ms.exception.DatosInvalidosException;
import cl.fullstack.pruducto_ms.exception.ErrorIntegridadDatosException;
import cl.fullstack.pruducto_ms.exception.RecursoNoEncontradoException;
import cl.fullstack.pruducto_ms.repository.CategoriaRepository;
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
    @Autowired
    private ClienteClient clienteClient;
    @Autowired
    private CategoriaRepository categoriaRepository;

    // @Override
    // public ProductoDTO createProducto(ProductoDTO dto) {
    // ProductoEntity producto = modelMapper.map(dto, ProductoEntity.class);
    // return modelMapper.map(productoRepository.save(producto), ProductoDTO.class);
    // }

    // metodo para crear producto
    @Override
    public ProductoDTO createProducto(ProductoDTO dto) {
        // Validar DTO nulo
        if (dto == null) {
            throw new DatosInvalidosException("Los datos del producto no pueden ser nulos");
        }

        // Validar nombre obligatorio
        if (dto.getNombreProducto() == null || dto.getNombreProducto().trim().isEmpty()) {
            throw new DatosInvalidosException("El nombre del producto es obligatorio");
        }

        // Validar existencia del cliente (desde cliente-ms)
        ClienteDTO cliente = clienteClient.obtenerClienteByRut(dto.getIdCliente());
        if (cliente == null) {
            throw new RecursoNoEncontradoException("Cliente con RUT " + dto.getIdCliente() + " no existe.");
        }

        // Validar existencia de la categoría (en la base de datos local)
        CategoriaEntity categoria = categoriaRepository.findById(dto.getIdCategoria())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Categoría con ID " + dto.getIdCategoria() + " no existe."));

        // Mapear y guardar
        ProductoEntity producto = modelMapper.map(dto, ProductoEntity.class);

        // Asociar categoría
        producto.setCategoria(categoria);

        // Guardar producto
        try {
            ProductoEntity guardado = productoRepository.save(producto);
            return modelMapper.map(guardado, ProductoDTO.class);
        } catch (DataIntegrityViolationException e) {
            throw new ErrorIntegridadDatosException("Error de integridad: verifique los datos ingresados.", e);
        }
    }

    // metodo para eliminar producto
    @Override
    public ProductoDTO eliminarProducto(int idProducto) {
        if (idProducto <= 0) {
            throw new DatosInvalidosException("El ID del producto debe ser mayor que cero");
        }

        ProductoEntity producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado con ID: " + idProducto));

        try {
            // Mapear entidad a DTO antes de eliminar
            ProductoDTO productoDTO = modelMapper.map(producto, ProductoDTO.class);

            productoRepository.delete(producto);

            return productoDTO; // Devuelvo DTO del producto eliminado
        } catch (DataIntegrityViolationException e) {
            throw new ErrorIntegridadDatosException(
                    "No se puede eliminar el producto porque está siendo utilizado en otro registro.", e);
        } catch (Exception e) {
            throw new RuntimeException("Error inesperado al eliminar el producto", e);
        }
    }

    @Override
    public ProductoDTO actualizarProducto(int idProducto, ProductoDTO dto) {
        if (dto == null) {
            throw new DatosInvalidosException("Los datos del producto no pueden ser nulos");
        }

        // Validar nombre obligatorio
        String nombre = dto.getNombreProducto();
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new DatosInvalidosException("El nombre del producto es obligatorio");
        }

        // Validar existencia del producto
        productoRepository.findById(idProducto)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado con ID: " + idProducto));

        // Validar existencia del cliente (microservicio cliente-ms)
        ClienteDTO cliente = clienteClient.obtenerClienteByRut(dto.getIdCliente());
        if (cliente == null) {
            throw new RecursoNoEncontradoException("Cliente con RUT " + dto.getIdCliente() + " no existe.");
        }

        // Validar existencia de la categoría (repositorio local)
        CategoriaEntity categoria = categoriaRepository.findById(dto.getIdCategoria())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Categoría con ID " + dto.getIdCategoria() + " no existe."));

        // Asignar ID del producto para evitar modificación del identificador
        dto.setIdProducto(idProducto);

        // Mapear y guardar
        ProductoEntity actualizado = modelMapper.map(dto, ProductoEntity.class);
        actualizado.setCategoria(categoria); // asegurar relación

        ProductoEntity guardado = productoRepository.save(actualizado);

        return modelMapper.map(guardado, ProductoDTO.class);
    }

    // metodo para obtener producto por id@Override
    public ProductoDTO obtenerProducto(int idProducto) {
        if (idProducto <= 0) {
            throw new DatosInvalidosException("El ID del producto debe ser mayor que cero");
        }

        ProductoEntity producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado con ID: " + idProducto));

        return modelMapper.map(producto, ProductoDTO.class);
    }

    // metodo para listar productos

    @Override
    public List<ProductoDTO> getAllProductos() {
        return productoRepository.findAll().stream()
                .map(producto -> modelMapper.map(producto, ProductoDTO.class))
                .collect(Collectors.toList());
    }

}
