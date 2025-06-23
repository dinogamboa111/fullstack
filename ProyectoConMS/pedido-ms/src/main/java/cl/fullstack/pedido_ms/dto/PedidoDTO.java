package cl.fullstack.pedido_ms.dto;

import java.util.List;

import lombok.Data;

@Data

public class PedidoDTO {

    private int idPedido;
    private int rutCliente;
    private char dvCliente;
    private String numCalle;
    private String nombreCalle;
    private int idComuna;
    private boolean estadoPedido;
    private List<DetallePedidoDTO> detallePedido;
    private Integer idDespachador;
    private Integer idCentro;
   
    
}
