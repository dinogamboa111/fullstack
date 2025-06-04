package cl.fullstack.pruducto_ms.service;



import java.util.List;

import cl.fullstack.pruducto_ms.dto.ProductoDTO;

public interface IProductoService {
    List<ProductoDTO> getAllProductos();
    ProductoDTO getProductoById(int idProducto);
    ProductoDTO createProducto(ProductoDTO productoDTO);
    ProductoDTO updateProducto(int idProducto, ProductoDTO productoDTO);
    void deleteProducto(int idProducto);
}