package cl.fullstack.pruducto_ms.service;

import java.util.List;

import cl.fullstack.pruducto_ms.dto.CategoriaDTO;

public interface ICategoriaService {
    List<CategoriaDTO> getAllCategorias();
    CategoriaDTO getCategoriaById(int idCategoria);
    CategoriaDTO createCategoria(CategoriaDTO categoriaDTO);
    CategoriaDTO updateCategoria(int idCategoria, CategoriaDTO categoriaDTO);
    void deleteCategoria(int idCategoria);
}