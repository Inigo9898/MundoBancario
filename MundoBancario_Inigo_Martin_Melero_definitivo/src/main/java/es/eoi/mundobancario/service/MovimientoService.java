package es.eoi.mundobancario.service;

import java.util.List;

import es.eoi.mundobancario.dto.MovimientoDto;
import es.eoi.mundobancario.entity.Amortizacion;
import es.eoi.mundobancario.entity.Cuenta;
import es.eoi.mundobancario.entity.Movimiento;
import es.eoi.mundobancario.entity.Prestamo;
import es.eoi.mundobancario.entity.TipoMovimiento;
import es.eoi.mundobancario.exception.CuentaNotFoundException;
import es.eoi.mundobancario.exception.MovimientoNotFoundException;
import es.eoi.mundobancario.exception.PagoErroneoException;

public interface MovimientoService {

	public Double saveIngreso(Movimiento mov, Integer id, TipoMovimiento tipo, Cuenta cuenta)
			throws CuentaNotFoundException, PagoErroneoException;

	public List<MovimientoDto> findMovimientosCuenta(Integer id) throws MovimientoNotFoundException;

	public void savePago(Movimiento mov, Integer id, TipoMovimiento tipo, Cuenta cuenta)
			throws CuentaNotFoundException, PagoErroneoException;

	public Double savePrestamo(Prestamo prestamo, Integer id, TipoMovimiento tipo, Cuenta cuenta)
			throws PagoErroneoException;

	public void saveAmortizacion(List<Amortizacion> devueltas, Integer id, TipoMovimiento tipo, Cuenta cuenta);

	public List<Movimiento> saveInteres(List<Amortizacion> amortizacion, TipoMovimiento tipo3);

}
