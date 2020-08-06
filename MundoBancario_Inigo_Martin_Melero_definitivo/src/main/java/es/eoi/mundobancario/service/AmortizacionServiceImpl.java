package es.eoi.mundobancario.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import es.eoi.mundobancario.entity.Amortizacion;
import es.eoi.mundobancario.entity.Prestamo;
import es.eoi.mundobancario.repository.AmortizacionRepository;

@Service
public class AmortizacionServiceImpl implements AmortizacionService {

	@Autowired
	AmortizacionRepository amortizacionRepository;

	@Override
	public List<Amortizacion> createAmortizacion(Prestamo prestamo) {
		Double cantidadpormes = prestamo.getImporte() / prestamo.getPlazos();
		Date fechainicial = prestamo.getFecha();
		Calendar cal = new GregorianCalendar();
		cal.setTime(fechainicial);
		List<Amortizacion> devueltas = new ArrayList<Amortizacion>();
		for (int i = 0; i < prestamo.getPlazos(); i++) {
			cal.add(Calendar.MONTH, 1);
			Date convertida = new Date(cal.getTimeInMillis());
			amortizacionRepository.save(new Amortizacion(convertida, cantidadpormes, prestamo));
			devueltas.add(new Amortizacion(convertida, cantidadpormes, prestamo));
		}
		return devueltas;
	}

	@Override
	public List<Amortizacion> ejecutarAmortizacionesPendientes() {
		Calendar cal = Calendar.getInstance();
		Date actual = new Date(cal.getTimeInMillis());
		return amortizacionRepository.findAmortizacionesActuales(actual);

	}
}
