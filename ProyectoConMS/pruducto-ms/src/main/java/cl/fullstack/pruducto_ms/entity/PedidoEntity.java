package cl.fullstack.pruducto_ms.entity;

import java.sql.Date;


import jakarta.persistence.Entity;

@Entity
public class PedidoEntity {

    private int id;
    
    private int idCliente;
    private Date fecha;
    private double total;

    

}
