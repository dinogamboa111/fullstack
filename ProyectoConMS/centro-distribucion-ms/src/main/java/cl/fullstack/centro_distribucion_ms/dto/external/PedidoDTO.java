package cl.fullstack.centro_distribucion_ms.dto.external;

import java.util.List;

import lombok.Data;

@Data
public class PedidoDTO {
    // private Integer idPedido;
    // private Long idDespachador;
    // // otros campos que necesites
        private int idPedido;
    private int rutCliente;
    private char dvCliente;
    private String numCalle;
    private String nombreCalle;
    private int idComuna;
    private boolean estadoPedido;
    private List<DetallePedidoDTO> detallePedido;
    private int idDespachador;
    
   
    
}
