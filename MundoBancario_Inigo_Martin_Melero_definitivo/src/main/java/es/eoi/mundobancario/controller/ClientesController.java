package es.eoi.mundobancario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import es.eoi.mundobancario.dto.ClienteDto;
import es.eoi.mundobancario.dto.CuentaDto;
import es.eoi.mundobancario.entity.Cliente;
import es.eoi.mundobancario.exception.ClienteNotFoundException;
import es.eoi.mundobancario.exception.CuentaNotFoundException;
import es.eoi.mundobancario.service.ClienteService;
import es.eoi.mundobancario.service.CuentaService;

@RestController
public class ClientesController {

	@Autowired
	private ClienteService clienteServiceImpl;

	@Autowired
	private CuentaService cuentaServiceImpl;

	@GetMapping("/clientes")
	public ResponseEntity<List<ClienteDto>> findAllClientes() throws ClienteNotFoundException {
		return ResponseEntity.ok(clienteServiceImpl.findAllClientes());
	}

	@GetMapping("/clientes/{id}")
	public ResponseEntity<ClienteDto> findClientebyId(@PathVariable Integer id) throws ClienteNotFoundException {
		return ResponseEntity.ok(clienteServiceImpl.findClientebyId(id));
	}

	@PostMapping("/clientes")
	public String createCliente(@RequestBody Cliente cli) {
		return clienteServiceImpl.saveCliente(cli);
	}

	@PutMapping(value = "clientes/{id}")
	public String updateCliente(@PathVariable Integer id, @RequestBody Cliente cli) throws ClienteNotFoundException {
		return clienteServiceImpl.updateCliente(id, cli);

	}

	@GetMapping("/clientes/{id}/cuentas")
	public ResponseEntity<List<CuentaDto>> findCuentasfromCliente(@PathVariable Integer id)
			throws ClienteNotFoundException, CuentaNotFoundException {
		clienteServiceImpl.findClientebyId(id);
		return ResponseEntity.ok(cuentaServiceImpl.findCuentasFromCliente(id));
	}

	@PostMapping("clientes/login")
	public ResponseEntity<ClienteDto> loginCliente(@RequestBody Cliente cli) throws ClienteNotFoundException {
		return ResponseEntity.ok(clienteServiceImpl.loginCliente(cli));

	}

	@ExceptionHandler({ ClienteNotFoundException.class })
	public String ClienteNotFound() {
		return "No existe el cliente o clientes buscados";
	}

	@ExceptionHandler({ CuentaNotFoundException.class })
	public String CuentaNotFound() {
		return "No existe la cuenta o cuentas buscadas";
	}

}
