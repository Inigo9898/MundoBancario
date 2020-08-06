package es.eoi.mundobancario.service;

import java.util.List;

import es.eoi.mundobancario.entity.Amortizacion;
import es.eoi.mundobancario.entity.Prestamo;

public interface AmortizacionService {

	public List<Amortizacion> createAmortizacion(Prestamo prestamo);

	public List<Amortizacion> ejecutarAmortizacionesPendientes();
}
