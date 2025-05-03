package cl.fullstack.pruducto_ms.service;



import java.util.List;

import cl.fullstack.pruducto_ms.dto.ProductoDTO;

public interface IProductoService {
    List<ProductoDTO> getAllProductos();
    ProductoDTO getProductoById(Long id);
    ProductoDTO createProducto(ProductoDTO productoDTO);
    ProductoDTO updateProducto(Long id, ProductoDTO productoDTO);
    void deleteProducto(Long id);
}