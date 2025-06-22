// package cl.fullstack.centro_distribucion_ms.service.impl;

// import java.util.List;
// import java.util.stream.Collectors;

// import org.modelmapper.ModelMapper;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// import cl.fullstack.centro_distribucion_ms.dto.DetalleGuiaDTO;
// import cl.fullstack.centro_distribucion_ms.dto.GuiaDespachoDTO;
// import cl.fullstack.centro_distribucion_ms.entity.DetalleGuiaEntity;
// import cl.fullstack.centro_distribucion_ms.entity.GuiaDespachoEntity;
// import cl.fullstack.centro_distribucion_ms.repository.GuiaDespachoRepository;
// import cl.fullstack.centro_distribucion_ms.service.IGuiaDespachoService;

// @Service
// public class GuiaDespachoServiceImpl implements IGuiaDespachoService {

//     @Autowired
//     private GuiaDespachoRepository guiaDespachoRepository;

//     @Autowired
//     private ModelMapper modelMapper;

//     @Override
//     public GuiaDespachoDTO crearGuiaDespacho(GuiaDespachoDTO guiaDespachoDTO) {
//         // Mapear DTO a entidad
//         GuiaDespachoEntity guiaEntity = modelMapper.map(guiaDespachoDTO, GuiaDespachoEntity.class);

//         // Asegurar que los detalles apunten a la entidad padre
//         if (guiaEntity.getPedidosAsociados() != null) {
//             guiaEntity.getPedidosAsociados().forEach(detalle -> detalle.setGuiaDespacho(guiaEntity));
//         }

//         GuiaDespachoEntity guiaGuardada = guiaDespachoRepository.save(guiaEntity);

//         // Mapear de vuelta a DTO y devolver
//         return modelMapper.map(guiaGuardada, GuiaDespachoDTO.class);
//     }



    
//     @Override
//     public List<GuiaDespachoDTO> obtenerTodasLasGuias() {
//         List<GuiaDespachoEntity> lista = guiaDespachoRepository.findAll();
//         return lista.stream()
//             .map(guia -> modelMapper.map(guia, GuiaDespachoDTO.class))
//             .collect(Collectors.toList());
//     }

//     @Override
//     public GuiaDespachoDTO obtenerGuiaPorId(int idGuia) {
//         GuiaDespachoEntity guia = guiaDespachoRepository.findById(idGuia)
//             .orElseThrow(() -> new RuntimeException("Guía no encontrada con id: " + idGuia));
//         return modelMapper.map(guia, GuiaDespachoDTO.class);
//     }

//     @Override
//     public void eliminarGuia(int idGuia) {
//         guiaDespachoRepository.deleteById(idGuia);
//     }

  
// }
package cl.fullstack.centro_distribucion_ms.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.fullstack.centro_distribucion_ms.Client.PedidoClient;
import cl.fullstack.centro_distribucion_ms.Client.UsuarioClient;
import cl.fullstack.centro_distribucion_ms.dto.GuiaDespachoResponseDTO;

import cl.fullstack.centro_distribucion_ms.dto.PedidoDTO;
import cl.fullstack.centro_distribucion_ms.dto.UsuarioDTO;
import cl.fullstack.centro_distribucion_ms.entity.DetalleGuiaEntity;
import cl.fullstack.centro_distribucion_ms.entity.GuiaDespachoEntity;
import cl.fullstack.centro_distribucion_ms.exception.DatosInvalidosException;
import cl.fullstack.centro_distribucion_ms.exception.RecursoNoEncontradoException;
import cl.fullstack.centro_distribucion_ms.repository.GuiaDespachoRepository;
import cl.fullstack.centro_distribucion_ms.service.IGuiaDespachoService;




@Service
public class GuiaDespachoServiceImpl implements IGuiaDespachoService {

    @Autowired
    private GuiaDespachoRepository guiaDespachoRepository;

    @Autowired
    private UsuarioClient usuarioClient;

    @Autowired
    private PedidoClient pedidoClient;

   
@Override
public GuiaDespachoResponseDTO crearGuiaDespacho(int idDespachador) {
    if ( idDespachador <= 0) {
        throw new DatosInvalidosException("ID del despachador inválido");
    }

    UsuarioDTO despachador = usuarioClient.obtenerUsuarioPorId(idDespachador);
    if (despachador == null) {
        throw new RecursoNoEncontradoException("Despachador no encontrado con ID: " + idDespachador);
    }

    List<PedidoDTO> pedidos = pedidoClient.obtenerPedidosPorDespachador(idDespachador);
    if (pedidos == null || pedidos.isEmpty()) {
        throw new DatosInvalidosException("No existen pedidos asociados al despachador");
    }

    GuiaDespachoEntity guia = new GuiaDespachoEntity();
    guia.setIdDespachador(idDespachador);
    GuiaDespachoEntity guiaGuardada = guiaDespachoRepository.save(guia);

    GuiaDespachoResponseDTO response = new GuiaDespachoResponseDTO();
    response.setIdGuia(guiaGuardada.getIdGuia());
    response.setIdDespachador(idDespachador);
    response.setPedidosAsociados(pedidos);  // <-- aquí pasas toda la lista completa de pedidos

    return response;
}



}
