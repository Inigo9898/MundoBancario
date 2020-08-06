package es.eoi.mundobancario.service;

import java.sql.Date;
import java.util.List;

import es.eoi.mundobancario.dto.PrestamoDto;
import es.eoi.mundobancario.dto.PrestamoDtoReport;
import es.eoi.mundobancario.entity.Cuenta;
import es.eoi.mundobancario.entity.Prestamo;
import es.eoi.mundobancario.exception.PrestamoErroneoException;
import es.eoi.mundobancario.exception.PrestamoNotFoundException;

public interface PrestamoService {

	public Prestamo createPrestamo(Prestamo prestamo, Cuenta cuenta) throws PrestamoErroneoException;

	public List<PrestamoDto> findPrestamosCuenta(Integer id) throws PrestamoNotFoundException;

	public List<PrestamoDto> findPrestamosVivosCuenta(Integer id) throws PrestamoNotFoundException;

	public List<PrestamoDto> findPrestamosAmortizadosCuenta(Integer id) throws PrestamoNotFoundException;

	public List<Prestamo> sacarPrestamos(Date date, Integer id, String string);

	public PrestamoDtoReport findPrestamosbyIdReport(Integer id) throws PrestamoNotFoundException;

	public List<PrestamoDtoReport> findPrestamoReports(String string);
	
}
