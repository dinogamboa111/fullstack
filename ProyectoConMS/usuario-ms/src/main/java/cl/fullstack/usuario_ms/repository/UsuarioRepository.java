package cl.fullstack.usuario_ms.repository;

import cl.fullstack.usuario_ms.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Integer> {

    @Query("SELECT u FROM UsuarioEntity u JOIN FETCH u.rol WHERE u.id = :id")
    Optional<UsuarioEntity> findByIdWithRol(@Param("id") Integer id);

    @Query("SELECT u FROM UsuarioEntity u WHERE u.rol.id = :rolId AND u.idComuna = :idComuna")
    Optional<UsuarioEntity> findDespachadorByRolIdAndComuna(@Param("rolId") Long rolId,
            @Param("idComuna") int idComuna);

    Optional<UsuarioEntity> findFirstByIdCentroAndRolId(int idCentro, int idRol);

}
