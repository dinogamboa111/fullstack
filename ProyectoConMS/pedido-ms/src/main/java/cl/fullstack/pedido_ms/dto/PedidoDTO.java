package cl.fullstack.pedido_ms.dto;

import java.util.List;

import lombok.Data;

@Data

public class PedidoDTO {

    private int idPedido;
    private int rutCliente;
    private int idMovimiento;
    private int idUsuario;
     private List<DetallePedidoDTO> productos;

}
