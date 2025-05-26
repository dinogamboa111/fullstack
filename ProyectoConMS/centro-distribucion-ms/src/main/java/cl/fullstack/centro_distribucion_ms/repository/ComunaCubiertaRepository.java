package cl.fullstack.centro_distribucion_ms.repository;

import cl.fullstack.centro_distribucion_ms.entity.ComunaCubiertaEntity;
import cl.fullstack.centro_distribucion_ms.entity.ComunaCubiertaPKcompuesta;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // indica que esta interfaz es un repositorio de datos
public interface ComunaCubiertaRepository extends JpaRepository<ComunaCubiertaEntity, ComunaCubiertaPKcompuesta> {  
    // repositorio para operaciones CRUD sobre comuna cubierta usando llave compuesta

 //////////////////////////////////////////////////////////////////////////   
    // Buscar por idCentro (sin filtro por idComuna)
    List<ComunaCubiertaEntity> findByIdCentro(int idCentro);
    
    // Buscar por idComuna (sin filtro por idCentro)
    List<ComunaCubiertaEntity> findByIdComuna(int idComuna);
///////////////////////////////////////////////////////////////////////////    
}


