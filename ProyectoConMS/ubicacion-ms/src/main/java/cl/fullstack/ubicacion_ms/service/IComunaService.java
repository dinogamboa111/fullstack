package cl.fullstack.ubicacion_ms.service;

import cl.fullstack.ubicacion_ms.dto.ComunaDTO;
import java.util.List;

public interface IComunaService {
    ComunaDTO crearComuna(ComunaDTO comuna);
    List<ComunaDTO> obtenerComunas();
    List<ComunaDTO> obtenerComunasPorProvincia(int idProvincia);
    ComunaDTO obtenerComunaPorId(int idComuna);
    void eliminarComuna(int idComuna);
}
