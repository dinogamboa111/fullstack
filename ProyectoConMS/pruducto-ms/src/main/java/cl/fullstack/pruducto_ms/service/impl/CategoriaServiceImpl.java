package cl.fullstack.pruducto_ms.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

import cl.fullstack.pruducto_ms.dto.CategoriaDTO;
import cl.fullstack.pruducto_ms.entity.CategoriaEntity;
import cl.fullstack.pruducto_ms.exception.DatosInvalidosException;

import cl.fullstack.pruducto_ms.exception.RecursoDuplicadoException;
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

    // metodo para crear categoria
    public CategoriaDTO crearCategoria(CategoriaDTO dto) {
        // Validar que el DTO no sea null
        if (dto == null) {
            throw new DatosInvalidosException("Los datos de la categoría no pueden ser nulos");
        }

        // Validar nombre obligatorio
        String nombre = dto.getNombreCategoria();
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new DatosInvalidosException("El nombre de la categoría es obligatorio");
        }

        // Validar descripción obligatoria
        String descripcion = dto.getDescripcion();
        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new DatosInvalidosException("La descripción de la categoría es obligatoria");
        }

        // Validar si la categoría ya existe
        if (categoriaRepository.findByNombreCategoria(nombre.trim()).isPresent()) {
            throw new RecursoDuplicadoException("Ya existe una categoría con el nombre: " + nombre);
        }

        CategoriaEntity nuevaCategoria = modelMapper.map(dto, CategoriaEntity.class);
        CategoriaEntity categoria = categoriaRepository.save(nuevaCategoria);
        return modelMapper.map(categoria, CategoriaDTO.class);

    }
    //metodo para eliminar categoria
    @Override
    public String eliminarCategoria(int idCategoria) {
        // Validar ID
        if (idCategoria <= 0) {
            throw new DatosInvalidosException("El ID de la categoría debe ser mayor que cero");
        }

        // se busca si la categoria existe en la bbdd
        CategoriaEntity categoria = categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new RecursoNoEncontradoException("Categoría no encontrada con ID: " + idCategoria));

        categoriaRepository.delete(categoria);
        return "Categoría eliminada con éxito.";

    }

    // metodo para actualizar categoria
    @Override
    public CategoriaDTO actualizarCategoria(int idCategoria, CategoriaDTO dto) {
        if (dto == null) {
            throw new DatosInvalidosException("Los datos de la categoría no pueden ser nulos");
        }

        String nombre = dto.getNombreCategoria();
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new DatosInvalidosException("El nombre de la categoría es obligatorio");
        }

        String descripcion = dto.getDescripcion();
        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new DatosInvalidosException("La descripción de la categoría es obligatoria");
        }

        categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new RecursoNoEncontradoException("Categoría no encontrada con ID: " + idCategoria));

        Optional<CategoriaEntity> duplicada = categoriaRepository.findByNombreCategoria(nombre.trim());

        boolean existeDuplicado = duplicada.isPresent() && duplicada.get().getIdCategoria() != idCategoria;

        if (existeDuplicado) {
            throw new RecursoDuplicadoException("Ya existe otra categoría con el nombre: " + nombre);
        }

        dto.setIdCategoria(idCategoria); // para asegurar ID

        CategoriaEntity actualizado = modelMapper.map(dto, CategoriaEntity.class);
        return modelMapper.map(categoriaRepository.save(actualizado), CategoriaDTO.class);
    }

    // metodo para obtener categoria
    @Override
    public CategoriaDTO obtenerCategoria(int idCategoria) {
        if (idCategoria <= 0) {
            throw new DatosInvalidosException("El ID de la categoría debe ser mayor que cero");
        }

        CategoriaEntity categoria = categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new RecursoNoEncontradoException("Categoría no encontrada con ID: " + idCategoria));

        return modelMapper.map(categoria, CategoriaDTO.class);
    }

    // metodo para listar categoria
    @Override
    public List<CategoriaDTO> listarCategorias() {
        return categoriaRepository.findAll().stream()
                .map(categoria -> modelMapper.map(categoria, CategoriaDTO.class))
                .collect(Collectors.toList());
    }

}
