package cl.fullstack.centro_distribucion_ms.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.fullstack.centro_distribucion_ms.Client.PedidoClient;
import cl.fullstack.centro_distribucion_ms.Client.UsuarioClient;
import cl.fullstack.centro_distribucion_ms.dto.DetalleGuiaDTO;
import cl.fullstack.centro_distribucion_ms.dto.GuiaDespachoDTO;
import cl.fullstack.centro_distribucion_ms.dto.GuiaDespachoResponseDTO;
import cl.fullstack.centro_distribucion_ms.dto.external.PedidoDTO;
import cl.fullstack.centro_distribucion_ms.dto.external.UsuarioDTO;
import cl.fullstack.centro_distribucion_ms.entity.DetalleGuiaEntity;
import cl.fullstack.centro_distribucion_ms.entity.GuiaDespachoEntity;
import cl.fullstack.centro_distribucion_ms.exception.DatosInvalidosException;
import cl.fullstack.centro_distribucion_ms.exception.RecursoNoEncontradoException;
import cl.fullstack.centro_distribucion_ms.repository.DetalleGuiaRepository;
import cl.fullstack.centro_distribucion_ms.repository.GuiaDespachoRepository;
import cl.fullstack.centro_distribucion_ms.service.IGuiaDespachoService;
import jakarta.transaction.Transactional;

@Service
public class GuiaDespachoServiceImpl implements IGuiaDespachoService {

    @Autowired
    private GuiaDespachoRepository guiaDespachoRepository;
    @Autowired
    private UsuarioClient usuarioClient;
    @Autowired
    private PedidoClient pedidoClient;
    @Autowired
    private DetalleGuiaRepository detalleGuiaRepository;

    // metodo para crear guia
    @Override
    public GuiaDespachoResponseDTO crearGuiaDespacho(int idDespachador) {
        if (idDespachador <= 0) {
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

        // Crear y guardar la guía de despacho
        GuiaDespachoEntity guia = new GuiaDespachoEntity();
        guia.setIdDespachador(idDespachador);
        GuiaDespachoEntity guiaGuardada = guiaDespachoRepository.save(guia);

        // Crear y guardar cada detalle de la guía
        for (PedidoDTO pedido : pedidos) {
            DetalleGuiaEntity detalle = new DetalleGuiaEntity();
            detalle.setGuiaDespacho(guiaGuardada); // para la relacion de manytomanu
            detalle.setIdPedido(pedido.getIdPedido()); // asociamos el id del pedido
            detalleGuiaRepository.save(detalle);
        }

        // Preparar y devolver el DTO de respuesta
        GuiaDespachoResponseDTO response = new GuiaDespachoResponseDTO();
        response.setIdGuia(guiaGuardada.getIdGuia());
        response.setIdDespachador(idDespachador);
        response.setPedidosAsociados(pedidos);

        return response;
    }

    // metodo para eliminar guia
    @Override
    @Transactional
    public String eliminarGuiaDespacho(int idGuia) {
        if (idGuia <= 0) {
            throw new DatosInvalidosException("ID de guía no valido");
        }

        GuiaDespachoEntity guia = guiaDespachoRepository.findById(idGuia)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró la guía con ID: " + idGuia));

        guiaDespachoRepository.delete(guia); //eliminamos la guia y sus detalles
        return "Guia eliminada con exito";
    }

    // metodo para obtener una guia por id
    @Override
    public GuiaDespachoDTO getGuiaDespachoById(int id) {
        if (id <= 0) {
            throw new DatosInvalidosException("ID de guía no valido");
        }

        GuiaDespachoEntity guia = guiaDespachoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Guía no encontrada con ID: " + id));

        GuiaDespachoDTO dto = new GuiaDespachoDTO();
        dto.setIdGuia(guia.getIdGuia());
        dto.setIdDespachador(guia.getIdDespachador());

        // Mapear detalles correctamente
        List<DetalleGuiaDTO> detallesDto = guia.getDetalles().stream()
                .map(detalle -> {
                    DetalleGuiaDTO detalleDto = new DetalleGuiaDTO();
                    detalleDto.setIdDetalle(detalle.getIdDetalle());
                    detalleDto.setIdPedido(detalle.getIdPedido());
                    return detalleDto; //
                })
                .toList();

        dto.setPedidosAsociados(detallesDto);
        return dto;
    }

    // metodo para obtener todas las guias

    @Override
    public List<GuiaDespachoDTO> getAllGuiasDespacho() {
        // Obtener todas las guías de despacho desde la base de datos
        List<GuiaDespachoEntity> guias = guiaDespachoRepository.findAll();

        // Si la lista está vacía, lanzar excepción personalizada
        if (guias.isEmpty()) {
            throw new RecursoNoEncontradoException("No hay guías registradas");
        }

        // Convertir cada entidad GuiaDespachoEntity en un DTO GuiaDespachoDTO
        return guias.stream().map(guia -> {
            GuiaDespachoDTO dto = new GuiaDespachoDTO();

            // Copiamos los atributos basicos de la guía
            dto.setIdGuia(guia.getIdGuia());
            dto.setIdDespachador(guia.getIdDespachador());

            // Mapear los detalles de la guía (DetalleGuiaEntity) a DetalleGuiaDTO
            List<DetalleGuiaDTO> detallesDto = guia.getDetalles().stream()
                    .map(detalle -> {
                        DetalleGuiaDTO detalleDto = new DetalleGuiaDTO();
                        detalleDto.setIdDetalle(detalle.getIdDetalle());
                        detalleDto.setIdPedido(detalle.getIdPedido());
                        return detalleDto; // retornamos cada DetalleGuiaDTO para la lista
                    })
                    .toList(); // Convertimos a una lista

            // asignamos los detalles al dto principal
            dto.setPedidosAsociados(detallesDto); //

            return dto; // retornamos la dto
        }).toList(); // tomamos todos los elementos y los creamos una lista

    // metodo para actualizar una guia FALTA

}
}