package cl.fullstack.pedido_ms.service;

import java.util.List;

import cl.fullstack.pedido_ms.dto.TipoMovimientoDTO;

public interface ITipoMovimientoService {

    List<TipoMovimientoDTO> getAllTipoMovimientos();

    TipoMovimientoDTO getTipoMovimientoById(int idTipoMovimiento);

    TipoMovimientoDTO createTipoMovimiento(TipoMovimientoDTO tipoMovimientoDTO);

    TipoMovimientoDTO updateTipoMovimiento(int idTipoMovimiento, TipoMovimientoDTO tipoMovimientoDTO);

    void deleteTipoMovimiento(int idTipoMovimiento);

}
