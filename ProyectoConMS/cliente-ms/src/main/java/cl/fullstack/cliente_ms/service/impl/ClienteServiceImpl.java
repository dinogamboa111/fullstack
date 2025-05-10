package cl.fullstack.cliente_ms.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.fullstack.cliente_ms.dto.ClienteDTO;
import cl.fullstack.cliente_ms.entity.ClienteEntity;
import cl.fullstack.cliente_ms.exception.CorreoDuplicadoException;
import cl.fullstack.cliente_ms.exception.RecursoNoEncontradoException;
import cl.fullstack.cliente_ms.repository.ClienteRepository;
import cl.fullstack.cliente_ms.service.IClienteService;

@Service
public class ClienteServiceImpl implements IClienteService {

    // usar rest template

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ClienteDTO crearCliente(ClienteDTO clienteDTO) {
        // Mapeamos el DTO a la entidad ClienteEntity
        ClienteEntity cliente = modelMapper.map(clienteDTO, ClienteEntity.class);

        // Guardamos el cliente en la base de datos
        ClienteEntity clienteGuardado = clienteRepository.save(cliente);

        // Devolvemos el cliente guardado como DTO
        return modelMapper.map(clienteGuardado, ClienteDTO.class);
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
        // Verificamos si el cliente existe con el RUT
        ClienteEntity clienteExistente = clienteRepository.findByRutCliente(rut)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente no encontrado con RUT: " + rut));

        // Verificamos si el correo ya está registrado con otro RUT antes de la
        // conversión
        Optional<ClienteEntity> clienteConCorreo = clienteRepository.findByEmail(dto.getEmail());

        // Si el correo está registrado con otro RUT, lanzamos una excepción
        if (clienteConCorreo.isPresent() && clienteConCorreo.get().getRutCliente() != rut) {
            throw new CorreoDuplicadoException("El correo electrónico ya está registrado con otro RUT.");
        }

        // Convertimos el DTO a la entidad y actualizamos los datos
        ClienteEntity actualizado = modelMapper.map(dto, ClienteEntity.class);
        actualizado.setRutCliente(rut); // Aseguramos que el RUT se mantenga consistente

        // Guardamos los cambios en la base de datos
        return modelMapper.map(clienteRepository.save(actualizado), ClienteDTO.class);
    }

    @Override
    public void eliminarCliente(int rut) {
        ClienteEntity cliente = clienteRepository.findById(rut)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente no encontrado con RUT: " + rut));
        clienteRepository.delete(cliente);
    }

}