package es.eoi.mundobancario.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import es.eoi.mundobancario.dto.ClienteDtoReport1;
import es.eoi.mundobancario.dto.PrestamoDtoReport;
import es.eoi.mundobancario.exception.ClienteNotFoundException;
import es.eoi.mundobancario.exception.PrestamoNotFoundException;
import es.eoi.mundobancario.pdfs.ClientePdf;
import es.eoi.mundobancario.pdfs.PrestamosPdf;
import es.eoi.mundobancario.service.ClienteService;
import es.eoi.mundobancario.service.PrestamoService;

@RestController
public class ReportsController {
	@Autowired
	PrestamoService prestamoServiceImpl;

	@Autowired
	ClienteService clienteServiceImpl;

	@GetMapping("/reports/clientes/{id}")
	public ResponseEntity<ClienteDtoReport1> findClientebyIdReport(@PathVariable Integer id)
			throws ClienteNotFoundException {
		return ResponseEntity.ok(clienteServiceImpl.findClientebyIdReport(id));
	}

	@PostMapping("/reports/clientes/{id}")
	public String createClientePdf(@PathVariable Integer id) throws ClienteNotFoundException, IOException {
		ClienteDtoReport1 devuelto = clienteServiceImpl.findClientebyIdReport(id);
		return ClientePdf.crearClientePdf(devuelto);
	}

	@GetMapping("/reports/prestamos/{id}")
	public ResponseEntity<PrestamoDtoReport> findPrestamosbyIdReport(@PathVariable Integer id)
			throws PrestamoNotFoundException {
		return ResponseEntity.ok(prestamoServiceImpl.findPrestamosbyIdReport(id));
	}

	@PostMapping("/reports/prestamos/{id}")
	public String createPrestamosPdf(@PathVariable Integer id) throws PrestamoNotFoundException, IOException {
		PrestamoDtoReport devuelto = prestamoServiceImpl.findPrestamosbyIdReport(id);
		return PrestamosPdf.crearPrestamoPdf(devuelto);
	}

	@GetMapping("/reports/prestamosVivos")
	public ResponseEntity<List<PrestamoDtoReport>> findPrestamosVivos() {
		return ResponseEntity.ok(prestamoServiceImpl.findPrestamoReports("VIVOS"));
	}

	@GetMapping("/reports/prestamosAmortizados")
	public ResponseEntity<List<PrestamoDtoReport>> findPrestamosAmortizados() {
		return ResponseEntity.ok(prestamoServiceImpl.findPrestamoReports("AMORTIZADOS"));
	}

	@ExceptionHandler({ ClienteNotFoundException.class })
	public String ClienteNotFound() {
		return "No existe el cliente buscado";
	}

	@ExceptionHandler({ PrestamoNotFoundException.class })
	public String PrestamoNotFound() {
		return "No existe el prestamo buscado";
	}

}
