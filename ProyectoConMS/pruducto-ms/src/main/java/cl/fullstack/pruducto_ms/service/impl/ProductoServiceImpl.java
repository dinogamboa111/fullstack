package cl.fullstack.pruducto_ms.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import cl.fullstack.pruducto_ms.dto.ProductoDTO;
import cl.fullstack.pruducto_ms.entity.CategoriaEntity;
import cl.fullstack.pruducto_ms.entity.ProductoEntity;
import cl.fullstack.pruducto_ms.exception.DatosInvalidosException;
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
    private CategoriaRepository categoriaRepository;

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

        // Validar ID del cliente
        if (dto.getIdCliente() <= 0) {
            throw new DatosInvalidosException("Debe proporcionar un ID de cliente válido");
        }

        // Validar existencia de la categoría
        CategoriaEntity categoria = categoriaRepository.findById(dto.getIdCategoria())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Categoría con ID " + dto.getIdCategoria() + " no existe."));

        // Mapear y asociar categoría
        ProductoEntity producto = modelMapper.map(dto, ProductoEntity.class);
        producto.setCategoria(categoria);

        // Guardar y retornar
        ProductoEntity guardado = productoRepository.save(producto);
        return modelMapper.map(guardado, ProductoDTO.class);
    }

    // metodo para eliminar producto
    @Override
    public String eliminarProducto(int idProducto) {
        if (idProducto <= 0) {
            throw new DatosInvalidosException("El ID del producto debe ser mayor que cero");
        }
        // buscamos si el producto esta en la bbdd
        ProductoEntity producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado con ID: " + idProducto));
        // eliminamos y mostramos msj para q se sepa que se elimino
        productoRepository.delete(producto);
        return "Producto eliminado con éxito";

    }

    // metodo para actualizar producto
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

        // Validar ID del cliente
        if (dto.getIdCliente() <= 0) {
            throw new DatosInvalidosException("Debe proporcionar un ID de cliente válido");
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

    // metodo para obtener producto por id
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

    // metodo para cargar productos en lote a la bbdd
    @Override
    public List<ProductoDTO> crearProductosEnLote(List<ProductoDTO> productos) {
        if (productos == null || productos.isEmpty()) {
            throw new DatosInvalidosException("La lista de productos no puede estar vacía");
        }

        return productos.stream()
                .map(this::createProducto) // usamos el metodo de crear productos individual
                .collect(Collectors.toList());
    }

}
