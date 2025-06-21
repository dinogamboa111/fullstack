package cl.fullstack.usuario_ms.repository;

import cl.fullstack.usuario_ms.entity.UsuarioEntity;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Integer> {

    @Query("SELECT u FROM UsuarioEntity u JOIN FETCH u.rol WHERE u.id = :id")
    Optional<UsuarioEntity> findByIdWithRol(@Param("id") Integer id);

    @Query(value = "SELECT * FROM usuario u WHERE u.id_centro = :idCentro AND ROWNUM = 1", nativeQuery = true)
    UsuarioEntity findFirstByIdCentro(@Param("idCentro") Integer idCentro);

    List<UsuarioEntity> findByIdCentroAndRolId(Integer idCentro, Integer rolId);

}
