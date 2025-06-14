package cl.fullstack.pruducto_ms.service;

import java.util.List;

import cl.fullstack.pruducto_ms.dto.CategoriaDTO;

public interface ICategoriaService {
    List<CategoriaDTO> listarCategorias();
    CategoriaDTO obtenerCategoria(int idCategoria);
    CategoriaDTO crearCategoria(CategoriaDTO categoriaDTO);
    CategoriaDTO actualizarCategoria(int idCategoria, CategoriaDTO categoriaDTO);
    CategoriaDTO eliminarCategoria(int idCategoria);
}