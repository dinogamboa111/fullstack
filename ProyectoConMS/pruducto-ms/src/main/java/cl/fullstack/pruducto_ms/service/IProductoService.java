package cl.fullstack.pruducto_ms.service;



import java.util.List;

import cl.fullstack.pruducto_ms.dto.ProductoDTO;

public interface IProductoService {
    List<ProductoDTO> getAllProductos();
    ProductoDTO obtenerProducto(int idProducto);
    ProductoDTO createProducto(ProductoDTO productoDTO);
    ProductoDTO actualizarProducto(int idProducto, ProductoDTO productoDTO);
    String eliminarProducto(int idProducto);
    List<ProductoDTO> crearProductosEnLote(List<ProductoDTO> productos);

}