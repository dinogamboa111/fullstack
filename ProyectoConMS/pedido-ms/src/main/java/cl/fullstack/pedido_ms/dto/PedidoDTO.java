package cl.fullstack.pedido_ms.dto;

import lombok.Data;

@Data

public class PedidoDTO {

    private Long idPedido;
    private int rutCliente;
    private Long idMovimiento;
    private int idUsuario;

}
