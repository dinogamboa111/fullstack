package cl.fullstack.pruducto_ms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.fullstack.pruducto_ms.dto.CategoriaDTO;
import cl.fullstack.pruducto_ms.entity.CategoriaEntity;
import cl.fullstack.pruducto_ms.repository.CategoriaRepository;
import cl.fullstack.pruducto_ms.service.ICategoriaService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaServiceImpl implements ICategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    public List<CategoriaDTO> getAllCategorias() {
        List<CategoriaEntity> categorias = categoriaRepository.findAll();
        return categorias.stream().map(categoria -> {
            CategoriaDTO dto = new CategoriaDTO();
            dto.setId(categoria.getId());
            dto.setNombre(categoria.getNombre());
            dto.setDescripcion(categoria.getDescripcion());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public CategoriaDTO getCategoriaById(Long id) {
        CategoriaEntity categoria = categoriaRepository.findById(id).orElse(null);
        if (categoria == null) return null;
        CategoriaDTO dto = new CategoriaDTO();
        dto.setId(categoria.getId());
        dto.setNombre(categoria.getNombre());
        dto.setDescripcion(categoria.getDescripcion());
        return dto;
    }

    @Override
    public CategoriaDTO createCategoria(CategoriaDTO categoriaDTO) {
        CategoriaEntity categoria = new CategoriaEntity();
        categoria.setNombre(categoriaDTO.getNombre());
        categoria.setDescripcion(categoriaDTO.getDescripcion());
        categoriaRepository.save(categoria);
        categoriaDTO.setId(categoria.getId());
        return categoriaDTO;
    }

    @Override
    public CategoriaDTO updateCategoria(Long id, CategoriaDTO categoriaDTO) {
        CategoriaEntity categoria = categoriaRepository.findById(id).orElse(null);
        if (categoria != null) {
            categoria.setNombre(categoriaDTO.getNombre());
            categoria.setDescripcion(categoriaDTO.getDescripcion());
            categoriaRepository.save(categoria);
            categoriaDTO.setId(categoria.getId());
        }
        return categoriaDTO;
    }

    @Override
    public void deleteCategoria(Long id) {
        categoriaRepository.deleteById(id);
    }
}
