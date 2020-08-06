package es.eoi.mundobancario.service;

import java.util.List;

import es.eoi.mundobancario.dto.ClienteDto;
import es.eoi.mundobancario.dto.ClienteDtoReport1;
import es.eoi.mundobancario.entity.Cliente;
import es.eoi.mundobancario.exception.ClienteNotFoundException;

public interface ClienteService {

	public List<ClienteDto> findAllClientes() throws ClienteNotFoundException;

	public ClienteDto findClientebyId(Integer id) throws ClienteNotFoundException;

	public String saveCliente(Cliente cli);

	public String updateCliente(Integer id, Cliente cli) throws ClienteNotFoundException;

	public ClienteDto loginCliente(Cliente cli) throws ClienteNotFoundException;

	public ClienteDtoReport1 findClientebyIdReport(Integer id) throws ClienteNotFoundException;
}
