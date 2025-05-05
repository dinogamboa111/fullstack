package cl.fullstack.pedido_ms.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.fullstack.pedido_ms.dto.TipoMovimientoDTO;
import cl.fullstack.pedido_ms.entity.TipoMovimientoEntity;
import cl.fullstack.pedido_ms.exception.RecursoNoEncontradoException;
import cl.fullstack.pedido_ms.repository.TipoMovimientoRepository;
import cl.fullstack.pedido_ms.service.ITipoMovimientoService;

@Service

public class TipoMovimientoImpl implements ITipoMovimientoService {

    @Autowired
    private TipoMovimientoRepository tipoMovimientoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public TipoMovimientoDTO createTipoMovimiento(TipoMovimientoDTO dto) {
        TipoMovimientoEntity tipoMovimiento = modelMapper.map(dto, TipoMovimientoEntity.class);
        return modelMapper.map(tipoMovimientoRepository.save(tipoMovimiento), TipoMovimientoDTO.class);
    }

    @Override
    public TipoMovimientoDTO getTipoMovimientoById(int idMovimiento) {
        TipoMovimientoEntity tipoMovimiento = tipoMovimientoRepository.findById(idMovimiento)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "tipo movimiento no encontrado con ID: " + idMovimiento));
        return modelMapper.map(tipoMovimiento, TipoMovimientoDTO.class);
    }

    @Override
    public List<TipoMovimientoDTO> getAllTipoMovimientos() {
        return tipoMovimientoRepository.findAll().stream()
                .map(tipoMovimiento -> modelMapper.map(tipoMovimiento, TipoMovimientoDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public TipoMovimientoDTO updateTipoMovimiento(int idMovimiento, TipoMovimientoDTO dto) {
        tipoMovimientoRepository.findById(idMovimiento)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "tipo movimeinto no encontrada con ID: " + idMovimiento));
        dto.setIdMovimiento(idMovimiento); // aseguramos que no cambie el ID
        TipoMovimientoEntity actualizado = modelMapper.map(dto, TipoMovimientoEntity.class);
        return modelMapper.map(tipoMovimientoRepository.save(actualizado), TipoMovimientoDTO.class);
    }

    @Override
    public void deleteTipoMovimiento(int idMovimiento) {
        TipoMovimientoEntity tipoMovimiento = tipoMovimientoRepository.findById(idMovimiento)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "tipo movimiento no encontrada con ID: " + idMovimiento));
        tipoMovimientoRepository.delete(tipoMovimiento);
    }

}
