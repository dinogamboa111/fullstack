package cl.fullstack.cliente_ms.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.fullstack.cliente_ms.dto.ClienteDTO;
import cl.fullstack.cliente_ms.entity.ClienteEntity;
import cl.fullstack.cliente_ms.exception.RecursoNoEncontradoException;
import cl.fullstack.cliente_ms.repository.ClienteRepository;
import cl.fullstack.cliente_ms.service.IClienteService;

@Service
public class ClienteServiceImpl implements IClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ClienteDTO crearCliente(ClienteDTO dto) {
        ClienteEntity cliente = modelMapper.map(dto, ClienteEntity.class);
        return modelMapper.map(clienteRepository.save(cliente), ClienteDTO.class);
    }

    @Override
    public ClienteDTO obtenerCliente(int rut) {
        ClienteEntity cliente = clienteRepository.findById(rut)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente no encontrado con RUT: " + rut));
        return modelMapper.map(cliente, ClienteDTO.class);
    }

    @Override
    public List<ClienteDTO> listarClientes() {
        return clienteRepository.findAll().stream()
                .map(cliente -> modelMapper.map(cliente, ClienteDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ClienteDTO actualizarCliente(int rut, ClienteDTO dto) {
        clienteRepository.findById(rut)
          .orElseThrow(() -> new RecursoNoEncontradoException("Cliente no encontrado con RUT: " + rut));
        dto.setRutCliente(rut); // aseguramos que no cambie el RUT
        ClienteEntity actualizado = modelMapper.map(dto, ClienteEntity.class);
        return modelMapper.map(clienteRepository.save(actualizado), ClienteDTO.class);
    }

    @Override
    public void eliminarCliente(int rut) {
        ClienteEntity cliente = clienteRepository.findById(rut)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente no encontrado con RUT: " + rut));
        clienteRepository.delete(cliente);
}
}   