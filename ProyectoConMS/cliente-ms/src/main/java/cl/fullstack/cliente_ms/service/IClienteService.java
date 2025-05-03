package cl.fullstack.cliente_ms.service;

import java.util.List;

import cl.fullstack.cliente_ms.dto.ClienteDTO;

public interface IClienteService {


    ClienteDTO crearCliente(ClienteDTO dto);
    ClienteDTO obtenerCliente(int rut);
    List<ClienteDTO> listarClientes();
    ClienteDTO actualizarCliente(int rut, ClienteDTO dto);
    void eliminarCliente(int rut);

}
