package es.eoi.mundobancario.service;

import java.util.List;
import java.util.NoSuchElementException;
import javax.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eoi.mundobancario.dto.ClienteDto;
import es.eoi.mundobancario.dto.ClienteDtoReport1;
import es.eoi.mundobancario.entity.Cliente;
import es.eoi.mundobancario.exception.ClienteNotFoundException;
import es.eoi.mundobancario.repository.ClienteRepository;
import es.eoi.mundobancario.utils.ObjectMapperUtils;

@Service
public class ClienteServiceImpl implements ClienteService {

	@Autowired
	ClienteRepository clienteRepository;

	@Autowired
	ModelMapper modelmapper;

	@Override
	public List<ClienteDto> findAllClientes() throws ClienteNotFoundException {

		List<Cliente> listaTodosClientes = clienteRepository.findAll();
		if (listaTodosClientes.isEmpty()) {
			throw new ClienteNotFoundException();
		}
		return ObjectMapperUtils.mapAll(listaTodosClientes, ClienteDto.class);
	}

	@Override
	public ClienteDto findClientebyId(Integer id) throws ClienteNotFoundException {
		try {
			Cliente cli = clienteRepository.findById(id).get();
			return modelmapper.map(cli, ClienteDto.class);
		} catch (NoSuchElementException e) {
			throw new ClienteNotFoundException();
		}
	}

	@Override
	public ClienteDto loginCliente(Cliente cli) throws ClienteNotFoundException {
		try {
			Cliente seleccionado = clienteRepository.findClienteByUsuario(cli.getUsuario());
			String pass_verdadera = seleccionado.getPass();
			if (pass_verdadera.equals(cli.getPass())) {
				return modelmapper.map(seleccionado, ClienteDto.class);
			} else {
				throw new ClienteNotFoundException();
			}
		} catch (NullPointerException e) {
			throw new ClienteNotFoundException();
		}
	}

	@Override
	public String updateCliente(Integer id, Cliente cli) throws ClienteNotFoundException {

		try {
			Cliente actualizado = clienteRepository.getOne(id);
			if (cli.getEmail().length() < 30) {
				actualizado.setEmail(cli.getEmail());
				clienteRepository.save(actualizado);
				return "Se ha actualizado correctamente el cliente";
			} else {
				return "Tienes que introducir menos de 30 caracteres de Email";
			}
		} catch (EntityNotFoundException e) {
			throw new ClienteNotFoundException();
		}

	}

	@Override
	public String saveCliente(Cliente cli) {

		if (cli.getNombre().length() > 20) {

			return "El nombre tiene mas de 20 caracteres, por favor reduzcalo";

		} else if (cli.getEmail().length() > 30) {

			return "El Email tiene mas de 30 caracteres, por favor reduzcalo";

		} else if (cli.getPass().length() > 20) {

			return "La contrasena tiene mas de 20 caracteres, por favor reduzcalo";

		} else if (cli.getUsuario().length() > 20) {

			return "El usuario tiene mas de 20 caracteres, por favor reduzcalo";

		} else {
			clienteRepository.save(cli);
			return "Se ha guardado con exito";
		}

	}

	@Override
	public ClienteDtoReport1 findClientebyIdReport(Integer id) throws ClienteNotFoundException {
		try {
			Cliente cli = clienteRepository.findById(id).get();
			return modelmapper.map(cli, ClienteDtoReport1.class);
		} catch (NoSuchElementException e) {
			throw new ClienteNotFoundException();
		}
	}

}
