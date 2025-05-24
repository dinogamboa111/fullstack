package cl.fullstack.centro_distribucion_ms.service;

import cl.fullstack.centro_distribucion_ms.dto.ComunaCubiertaDTO;
import java.util.List;


public interface IComunaCubiertaService {
    
    // recibe un dto de comuna cubierta, lo guarda y devuelve el dto guardado
    ComunaCubiertaDTO crearComunaCubierta(ComunaCubiertaDTO comunaCubierta);

    // devuelve la lista completa de comunas cubiertas
    List<ComunaCubiertaDTO> obtenerTodas(); 

    // recibe un id y devuelve la comuna cubierta correspondiente
    ComunaCubiertaDTO obtenerPorId(Long id); 
    
    // recibe un id y elimina la comuna cubierta correspondiente
    void eliminarPorId(Long id); 
}