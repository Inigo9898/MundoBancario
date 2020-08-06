package es.eoi.mundobancario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import es.eoi.mundobancario.dto.CuentaDto;
import es.eoi.mundobancario.dto.MovimientoDto;
import es.eoi.mundobancario.dto.PrestamoDto;
import es.eoi.mundobancario.entity.Amortizacion;
import es.eoi.mundobancario.entity.Cuenta;
import es.eoi.mundobancario.entity.Movimiento;
import es.eoi.mundobancario.entity.Prestamo;
import es.eoi.mundobancario.entity.TipoMovimiento;
import es.eoi.mundobancario.exception.ClienteNotFoundException;
import es.eoi.mundobancario.exception.CuentaNotFoundException;
import es.eoi.mundobancario.exception.MovimientoNotFoundException;
import es.eoi.mundobancario.exception.PagoErroneoException;
import es.eoi.mundobancario.exception.PrestamoErroneoException;
import es.eoi.mundobancario.exception.PrestamoNotFoundException;
import es.eoi.mundobancario.service.AmortizacionService;
import es.eoi.mundobancario.service.ClienteService;
import es.eoi.mundobancario.service.CuentaService;
import es.eoi.mundobancario.service.MovimientoService;
import es.eoi.mundobancario.service.PrestamoService;
import es.eoi.mundobancario.service.TipoMovimientoService;

@RestController
public class CuentasController {
	@Autowired
	private CuentaService cuentaServiceImpl;

	@Autowired
	private ClienteService clienteServiceImpl;

	@Autowired
	private MovimientoService movimientoServiceImpl;

	@Autowired
	private TipoMovimientoService tipoMovimientoServiceImpl;

	@Autowired
	private PrestamoService prestamoServiceImpl;

	@Autowired
	private AmortizacionService amortizacionServiceImpl;

	@GetMapping("/cuentas")
	public ResponseEntity<List<CuentaDto>> findAll() throws CuentaNotFoundException {
		return ResponseEntity.ok(cuentaServiceImpl.findAllCuentas());
	}

	@GetMapping("/cuentas/deudoras")
	public ResponseEntity<List<CuentaDto>> findCuentasDeudoras() throws CuentaNotFoundException {
		return ResponseEntity.ok(cuentaServiceImpl.findCuentasDeudoras());
	}

	@GetMapping("/cuentas/{id}")
	public ResponseEntity<CuentaDto> findbyId(@PathVariable Integer id) throws CuentaNotFoundException {
		return ResponseEntity.ok(cuentaServiceImpl.findCuentabyId(id));
	}

	@PostMapping("/cuentas")
	public String create(@RequestBody Cuenta cuenta) throws ClienteNotFoundException {
		clienteServiceImpl.findClientebyId(cuenta.getCliente().getId());
		return cuentaServiceImpl.saveCuenta(cuenta);
	}

	@PutMapping("cuentas/{id}")
	public String updateCuenta(@PathVariable Integer id, @RequestBody Cuenta cuenta) throws CuentaNotFoundException {
		return cuentaServiceImpl.updateCuenta(id, cuenta);
	}

	@GetMapping("/cuentas/{id}/movimientos")
	public ResponseEntity<List<MovimientoDto>> findMovimientosCuenta(@PathVariable Integer id)
			throws CuentaNotFoundException, MovimientoNotFoundException {
		cuentaServiceImpl.findCuentabyId(id);
		return ResponseEntity.ok(movimientoServiceImpl.findMovimientosCuenta(id));
	}

	@GetMapping("/cuentas/{id}/prestamos")
	public ResponseEntity<List<PrestamoDto>> findPrestamosCuenta(@PathVariable Integer id)
			throws CuentaNotFoundException, PrestamoNotFoundException {
		cuentaServiceImpl.findCuentabyId(id);
		return ResponseEntity.ok(prestamoServiceImpl.findPrestamosCuenta(id));
	}

	@GetMapping("/cuentas/{id}/prestamosVivos")
	public ResponseEntity<List<PrestamoDto>> findPrestamosVivosCuenta(@PathVariable Integer id)
			throws CuentaNotFoundException, PrestamoNotFoundException {
		cuentaServiceImpl.findCuentabyId(id);
		return ResponseEntity.ok(prestamoServiceImpl.findPrestamosVivosCuenta(id));
	}

	@GetMapping("/cuentas/{id}/prestamosAmortizados")
	public ResponseEntity<List<PrestamoDto>> findPrestamosAmortizadosCuenta(@PathVariable Integer id)
			throws CuentaNotFoundException, PrestamoNotFoundException {
		cuentaServiceImpl.findCuentabyId(id);
		return ResponseEntity.ok(prestamoServiceImpl.findPrestamosAmortizadosCuenta(id));
	}

	@PostMapping("/cuentas/{id}/prestamos")
	public String createPrestamo(@PathVariable Integer id, @RequestBody Prestamo prestamo)
			throws CuentaNotFoundException, PrestamoErroneoException, PagoErroneoException {
		Cuenta cuenta = cuentaServiceImpl.findCuentabyId2(id);
		Prestamo prestamo2 = prestamoServiceImpl.createPrestamo(prestamo, cuenta);
		List<Amortizacion> amortizaciones = amortizacionServiceImpl.createAmortizacion(prestamo2);
		TipoMovimiento tipo = tipoMovimientoServiceImpl.ReadTipoMovimiento("PRESTAMO");
		Double importePrestamo = movimientoServiceImpl.savePrestamo(prestamo, id, tipo, cuenta);
		cuentaServiceImpl.cambiarsaldo(importePrestamo, id, "PRESTAMO");
		TipoMovimiento tipo2 = tipoMovimientoServiceImpl.ReadTipoMovimiento("AMORTIZACION");
		movimientoServiceImpl.saveAmortizacion(amortizaciones, id, tipo2, cuenta);
		return "El prestamo y las amortizaciones han sido creadas";
	}

	@PostMapping("/cuentas/{id}/ingresos")
	public String createIngreso(@PathVariable Integer id, @RequestBody Movimiento mov)
			throws CuentaNotFoundException, PagoErroneoException {
		Cuenta cuenta = cuentaServiceImpl.findCuentabyId2(id);
		TipoMovimiento tipo = tipoMovimientoServiceImpl.ReadTipoMovimiento("INGRESO");
		Double importeIngreso = movimientoServiceImpl.saveIngreso(mov, id, tipo, cuenta);
		cuentaServiceImpl.cambiarsaldo(importeIngreso, id, "INGRESO");
		return "Se ha creado el ingreso";
	}

	@PostMapping("/cuentas/{id}/pagos")
	public String createPago(@PathVariable Integer id, @RequestBody Movimiento mov)
			throws CuentaNotFoundException, PagoErroneoException {
		Cuenta cuenta = cuentaServiceImpl.findCuentabyId2(id);
		cuentaServiceImpl.cambiarsaldo(mov.getImporte(), id, "PAGO");
		TipoMovimiento tipo = tipoMovimientoServiceImpl.ReadTipoMovimiento("PAGO");
		movimientoServiceImpl.savePago(mov, id, tipo, cuenta);
		return "Se ha creado el pago";
	}

	@PostMapping("/cuentas/ejecutarAmortizacionesDiarias")
	@Scheduled(fixedRate = 86400000) // 86400000 ms = 1 dia. Esta anotacion funcionará si el servidor está todo el
										// día encendido.
	public String Amortizacionesdiarias() throws CuentaNotFoundException, PagoErroneoException {

		List<Amortizacion> actuales = amortizacionServiceImpl.ejecutarAmortizacionesPendientes();
		TipoMovimiento tipo3 = tipoMovimientoServiceImpl.ReadTipoMovimiento("INTERESES");
		List<Movimiento> movimientos = movimientoServiceImpl.saveInteres(actuales, tipo3);

		for (int i = 0; i < movimientos.size(); i++) {
			cuentaServiceImpl.cambiarsaldo(movimientos.get(i).getImporte() + actuales.get(i).getImporte(),
					movimientos.get(i).getCuenta().getNum_cuenta(), "INTERESES");
		}
		return "Se han actualizado los intereses de " + movimientos.size() + " cuentas.";
	}

	@ExceptionHandler({ CuentaNotFoundException.class })
	public String CuentaNotFound() {
		return "No existe la cuenta buscada";
	}

	@ExceptionHandler({ PagoErroneoException.class })
	public String PagoErroneo() {
		return "No puede pagar y quedarse con saldo negativo";
	}

	@ExceptionHandler({ ClienteNotFoundException.class })
	public String ClienteNotFound() {
		return "No existe el cliente";
	}

	@ExceptionHandler({ MovimientoNotFoundException.class })
	public String MovimientoNotFound() {
		return "No existen movimientos en esta cuenta";
	}

	@ExceptionHandler({ PrestamoNotFoundException.class })
	public String PrestamoNotFound() {
		return "No existen prestamos en esta cuenta";
	}

	@ExceptionHandler({ PrestamoErroneoException.class })
	public String PrestamoErroneo() {
		return "No se puede pedir un prestamo, ya tiene uno en curso";
	}
}
