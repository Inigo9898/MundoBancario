package es.eoi.mundobancario.service;

import java.util.List;

import es.eoi.mundobancario.dto.CuentaDto;
import es.eoi.mundobancario.entity.Cuenta;
import es.eoi.mundobancario.exception.CuentaNotFoundException;
import es.eoi.mundobancario.exception.PagoErroneoException;

public interface CuentaService {

	public List<CuentaDto> findAllCuentas() throws CuentaNotFoundException;

	public CuentaDto findCuentabyId(Integer id) throws CuentaNotFoundException;

	public Cuenta findCuentabyId2(Integer id) throws CuentaNotFoundException;

	public String saveCuenta(Cuenta cuenta);

	public List<CuentaDto> findCuentasFromCliente(Integer id) throws CuentaNotFoundException;

	public String updateCuenta(Integer id, Cuenta cuenta) throws CuentaNotFoundException;

	public List<CuentaDto> findCuentasDeudoras() throws CuentaNotFoundException;

	public void cambiarsaldo(double importe, Integer id, String operacion) throws PagoErroneoException;

}
