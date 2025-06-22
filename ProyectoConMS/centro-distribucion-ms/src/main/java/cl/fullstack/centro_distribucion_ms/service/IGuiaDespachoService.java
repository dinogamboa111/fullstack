
package cl.fullstack.centro_distribucion_ms.service;

import java.util.List;

import cl.fullstack.centro_distribucion_ms.dto.GuiaDespachoDTO;
import cl.fullstack.centro_distribucion_ms.dto.GuiaDespachoResponseDTO;

public interface IGuiaDespachoService {

    GuiaDespachoResponseDTO crearGuiaDespacho(int idDespachador);

    String eliminarGuiaDespacho(int idGuia);

    GuiaDespachoDTO getGuiaDespachoById(int id);
    
    List<GuiaDespachoDTO> getAllGuiasDespacho();
}
