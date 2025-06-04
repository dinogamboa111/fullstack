package cl.fullstack.pruducto_ms.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.fullstack.pruducto_ms.dto.CategoriaDTO;
import cl.fullstack.pruducto_ms.entity.CategoriaEntity;
import cl.fullstack.pruducto_ms.exception.RecursoNoEncontradoException;
import cl.fullstack.pruducto_ms.repository.CategoriaRepository;
import cl.fullstack.pruducto_ms.service.ICategoriaService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaServiceImpl implements ICategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoriaDTO createCategoria(CategoriaDTO dto) {
        CategoriaEntity categoria = modelMapper.map(dto, CategoriaEntity.class);
        return modelMapper.map(categoriaRepository.save(categoria), CategoriaDTO.class);
    }

    @Override
    public CategoriaDTO getCategoriaById(int idCategoria) {
        CategoriaEntity categoria = categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new RecursoNoEncontradoException("Categoria no encontrada con ID: " + idCategoria));
        return modelMapper.map(categoria, CategoriaDTO.class);
    }

    @Override
    public List<CategoriaDTO> getAllCategorias() {
        return categoriaRepository.findAll().stream()
                .map(categoria -> modelMapper.map(categoria, CategoriaDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public CategoriaDTO updateCategoria(int idCategoria, CategoriaDTO dto) {
        categoriaRepository.findById(idCategoria)
          .orElseThrow(() -> new RecursoNoEncontradoException("Categoria no encontrada con ID: " + idCategoria));
        dto.setIdCategoria(idCategoria); // aseguramos que no cambie el ID
        CategoriaEntity actualizado = modelMapper.map(dto, CategoriaEntity.class);
        return modelMapper.map(categoriaRepository.save(actualizado), CategoriaDTO.class);
    }

    @Override
    public void deleteCategoria(int idCategoria) {
        CategoriaEntity categoria = categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new RecursoNoEncontradoException("Categoria no encontrada con ID: " + idCategoria));
        categoriaRepository.delete(categoria);
    }
}
