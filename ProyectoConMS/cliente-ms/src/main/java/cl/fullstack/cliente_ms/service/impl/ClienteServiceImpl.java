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
import cl.fullstack.cliente_ms.exception.RecursoDuplicadoException;
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
    public ClienteDTO crearCliente(ClienteDTO dto) {
        System.out.println("hola 2");
        // validamos si existe ya el rut en la bbdd
        if (clienteRepository.verificarExistenciaPorRut(dto.getRutCliente())) { // existBy da error por diversos motivos, asi que hice la consulta con una query y la guarde en el metodo que estoy aplicando aqui
            throw new RecursoDuplicadoException("Ya existe un cliente con el RUT: " + dto.getRutCliente());

        }

        // Verifica si el correo ya está registrado
        if (clienteRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new CorreoDuplicadoException("El correo electrónico ya esta registrado");
        }

        // paso la dto a entity y lo guardo en la bbdd 
        ClienteEntity nuevoCliente = modelMapper.map(dto, ClienteEntity.class);
        ClienteEntity guardado = clienteRepository.save(nuevoCliente);
        return modelMapper.map(guardado, ClienteDTO.class);
    }

    // @Override
    // public ClienteDTO crearCliente(ClienteDTO clienteDTO) {
    // // Mapeamos el DTO a la entidad ClienteEntity
    // ClienteEntity cliente = modelMapper.map(clienteDTO, ClienteEntity.class);

    // // Guardamos el cliente en la base de datos
    // ClienteEntity clienteGuardado = clienteRepository.save(cliente);

    // // Devolvemos el cliente guardado como DTO
    // return modelMapper.map(clienteGuardado, ClienteDTO.class);
    // }

    @Override
    public ClienteDTO obtenerCliente(int rut) {
        Optional<ClienteEntity> clienteOptional = clienteRepository.findById(rut);

        return clienteOptional
                .map(cliente -> modelMapper.map(cliente, ClienteDTO.class))
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente no encontrado con RUT: " + rut));
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
        clienteRepository.findByRutCliente(rut)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente no encontrado con RUT: " + rut));

        // si correo ya está registrado con otro RUT antes de la
        // conversión lo campturamos para lanzar el error
        Optional<ClienteEntity> clienteConCorreo = clienteRepository.findByEmail(dto.getEmail());

        // Si el correo está registrado con otro RUT, lanzamos una excepción
        if (clienteConCorreo.isPresent() && clienteConCorreo.get().getRutCliente() != rut) {
            throw new CorreoDuplicadoException("El correo electrónico esta en uso.");
        }

        // Convertimos el DTO a la entidad y actualizamos los datos
        ClienteEntity clienteActualizado = modelMapper.map(dto, ClienteEntity.class);
        clienteActualizado.setRutCliente(rut); // aqui se hace sto para conservar el rut original que es una PK y
                                               // guardamos el cambio

        // Guardamos los cambios en la base de datos
        return modelMapper.map(clienteRepository.save(clienteActualizado), ClienteDTO.class);
    }

    @Override
    public void eliminarCliente(int rut) {
        ClienteEntity cliente = clienteRepository.findById(rut)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente no encontrado con RUT: " + rut));
        clienteRepository.delete(cliente);
    }

}