package es.eoi.mundobancario.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eoi.mundobancario.dto.MovimientoDto;
import es.eoi.mundobancario.entity.Amortizacion;
import es.eoi.mundobancario.entity.Cuenta;
import es.eoi.mundobancario.entity.Movimiento;
import es.eoi.mundobancario.entity.Prestamo;
import es.eoi.mundobancario.entity.TipoMovimiento;
import es.eoi.mundobancario.exception.CuentaNotFoundException;
import es.eoi.mundobancario.exception.MovimientoNotFoundException;
import es.eoi.mundobancario.exception.PagoErroneoException;
import es.eoi.mundobancario.repository.MovimientoRepository;
import es.eoi.mundobancario.utils.ObjectMapperUtils;

@Service
public class MovimientoServiceImpl implements MovimientoService {

	@Autowired
	MovimientoRepository movimientoRepository;

	@Override
	public List<MovimientoDto> findMovimientosCuenta(Integer id) throws MovimientoNotFoundException {
		List<Movimiento> returned = movimientoRepository.findMovimientoCuenta(id);
		if (returned.isEmpty()) {
			throw new MovimientoNotFoundException();
		}
		return ObjectMapperUtils.mapAll(returned, MovimientoDto.class);
	}

	@Override
	public Double savePrestamo(Prestamo prestamo, Integer id, TipoMovimiento tipo, Cuenta cuen)
			throws PagoErroneoException {

		Movimiento mov = new Movimiento();
		mov.setCuenta(cuen);
		mov.setTipomovimiento(tipo);
		mov.setDescripcion(prestamo.getDescripcion());
		mov.setFecha(prestamo.getFecha());
		mov.setImporte(prestamo.getImporte());
		movimientoRepository.save(mov);
		return prestamo.getImporte();
	}

	@Override
	public void saveAmortizacion(List<Amortizacion> devueltas, Integer id, TipoMovimiento tipo, Cuenta cuen) {

		for (int i = 0; i < devueltas.size(); i++) {
			Movimiento mov = new Movimiento();
			mov.setDescripcion("Amortizacion de prestamo");
			mov.setCuenta(cuen);
			mov.setTipomovimiento(tipo);
			mov.setFecha(devueltas.get(i).getFecha());
			mov.setImporte(devueltas.get(i).getImporte());
			movimientoRepository.save(mov);
		}
		// Con las amortizaciones, NO se cambia el saldo. Solo se cambia el saldo al
		// ejecutar las AmortizacionesDiarias
	}

	@Override
	public Double saveIngreso(Movimiento mov, Integer id, TipoMovimiento tipo, Cuenta cuen)
			throws CuentaNotFoundException, PagoErroneoException {

		mov.setCuenta(cuen);
		mov.setTipomovimiento(tipo);
		movimientoRepository.save(mov);
		return mov.getImporte();
	}

	@Override
	public void savePago(Movimiento mov, Integer id, TipoMovimiento tipo, Cuenta cuen)
			throws CuentaNotFoundException, PagoErroneoException {
		mov.setCuenta(cuen);
		mov.setTipomovimiento(tipo);
		movimientoRepository.save(mov);
	}

	@Override
	public List<Movimiento> saveInteres(List<Amortizacion> amortizacion, TipoMovimiento tipo3) {
		List<Movimiento> lista = new ArrayList<Movimiento>();

		for (int i = 0; i < amortizacion.size(); i++) {
			Cuenta cuen = amortizacion.get(i).getPrestamo().getCuenta();
			Movimiento mov = new Movimiento();
			mov.setDescripcion("Intereses del prestamo");
			mov.setCuenta(cuen);
			mov.setTipomovimiento(tipo3);
			mov.setFecha(amortizacion.get(i).getFecha());
			Double interes = amortizacion.get(i).getImporte() * 0.02;
			mov.setImporte(interes);
			movimientoRepository.save(mov);
			lista.add(mov);
		}
		return lista;
	}
}
