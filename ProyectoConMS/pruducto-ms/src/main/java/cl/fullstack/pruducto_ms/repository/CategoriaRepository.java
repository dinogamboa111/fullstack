package cl.fullstack.pruducto_ms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import cl.fullstack.pruducto_ms.entity.CategoriaEntity;

@Repository
public interface CategoriaRepository extends JpaRepository<CategoriaEntity, Integer> {

    // Optional<CategoriaEntity> findByIdCategoria(int idCategoria);
    Optional<CategoriaEntity> findByNombreCategoria(String nombreCategoria);
    // boolean findByNombreCategoria(String nombreCategoria);
}