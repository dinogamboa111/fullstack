package cl.fullstack.pruducto_ms.service;



import java.util.List;

import cl.fullstack.pruducto_ms.dto.ProductoDTO;

public interface IProductoService {
    List<ProductoDTO> getAllProductos();
    ProductoDTO getProductoById(int id);
    ProductoDTO createProducto(ProductoDTO productoDTO);
    ProductoDTO updateProducto(int id, ProductoDTO productoDTO);
    void deleteProducto(int id);
}