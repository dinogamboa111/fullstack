package cl.fullstack.centro_distribucion_ms.service;

import cl.fullstack.centro_distribucion_ms.dto.ComunaCubiertaDTO;
import java.util.List;

public interface IComunaCubiertaService {
    
    // recibe un dto de comuna cubierta, lo guarda y devuelve el dto guardado
    ComunaCubiertaDTO crearComunaCubierta(ComunaCubiertaDTO comunaCubierta);

    // devuelve la lista completa de comunas cubiertas
    List<ComunaCubiertaDTO> obtenerTodas(); 

    // recibe el id del centro y el id de la comuna y devuelve la comuna cubierta correspondiente
    ComunaCubiertaDTO obtenerPorId(int idCentro, int idComuna); 
    
    // recibe el id del centro y el id de la comuna y elimina la comuna cubierta correspondiente
    void eliminarPorId(int idCentro, int idComuna);

   
    // recibe el id de un centro y devuelve todas sus comunas cubiertas asociadas
    List<ComunaCubiertaDTO> obtenerPorIdCentro(int idCentro);

    // recibe el id de una comuna y devuelve todas sus ocurrencias en diferentes centros
    List<ComunaCubiertaDTO> obtenerPorIdComuna(int idComuna);
}
