// package cl.fullstack.centro_distribucion_ms.service;

// import java.util.List;

// import cl.fullstack.centro_distribucion_ms.dto.GuiaDespachoDTO;

// public interface IGuiaDespachoService {

//     GuiaDespachoDTO crearGuiaDespacho(GuiaDespachoDTO guiaDespachoDTO);

//     List<GuiaDespachoDTO> obtenerTodasLasGuias();

//     GuiaDespachoDTO obtenerGuiaPorId(int idGuia);

//     void eliminarGuia(int idGuia);

  

// }
package cl.fullstack.centro_distribucion_ms.service;

import cl.fullstack.centro_distribucion_ms.dto.GuiaDespachoResponseDTO;

public interface IGuiaDespachoService {
    GuiaDespachoResponseDTO crearGuiaDespacho(int idDespachador);
}
