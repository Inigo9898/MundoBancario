package es.eoi.mundobancario.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.NoSuchElementException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import es.eoi.mundobancario.dto.PrestamoDto;
import es.eoi.mundobancario.dto.PrestamoDtoReport;
import es.eoi.mundobancario.entity.Amortizacion;
import es.eoi.mundobancario.entity.Cuenta;
import es.eoi.mundobancario.entity.Prestamo;
import es.eoi.mundobancario.exception.PrestamoErroneoException;
import es.eoi.mundobancario.exception.PrestamoNotFoundException;
import es.eoi.mundobancario.repository.PrestamoRepository;
import es.eoi.mundobancario.utils.ObjectMapperUtils;

@Service
public class PrestamoServiceImpl implements PrestamoService {

	@Autowired
	PrestamoRepository prestamoRepository;

	@Autowired
	ModelMapper modelmapper;

	@Override
	public List<PrestamoDto> findPrestamosCuenta(Integer id) throws PrestamoNotFoundException {
		List<Prestamo> returned = prestamoRepository.findPrestamosCuenta(id);
		if (returned.isEmpty()) {
			throw new PrestamoNotFoundException();
		}
		return ObjectMapperUtils.mapAll(returned, PrestamoDto.class);
	}

	@Override
	public List<PrestamoDto> findPrestamosVivosCuenta(Integer id) throws PrestamoNotFoundException {
		Calendar cal = Calendar.getInstance();
		Date actual = new Date(cal.getTimeInMillis());
		List<Prestamo> prestamosVivos = sacarPrestamos(actual, id, "VIVOS");
		if (prestamosVivos.isEmpty()) {
			throw new PrestamoNotFoundException();
		}
		return ObjectMapperUtils.mapAll(prestamosVivos, PrestamoDto.class);
	}

	@Override
	public List<PrestamoDto> findPrestamosAmortizadosCuenta(Integer id) throws PrestamoNotFoundException {
		Calendar cal = Calendar.getInstance();
		Date actual = new Date(cal.getTimeInMillis());
		List<Prestamo> prestamosAmortizados = sacarPrestamos(actual, id, "AMORTIZADOS");
		if (prestamosAmortizados.isEmpty()) {
			throw new PrestamoNotFoundException();
		}
		return ObjectMapperUtils.mapAll(prestamosAmortizados, PrestamoDto.class);
	}

	@Override
	public List<Prestamo> sacarPrestamos(Date date, Integer id, String string) {
		List<Prestamo> existentes = prestamoRepository.findPrestamosCuenta(id);
		List<Prestamo> devueltos = new ArrayList<Prestamo>();
		for (int i = 0; i < existentes.size(); i++) {
			List<Amortizacion> lista = existentes.get(i).getAmortizaciones();
			Date maxDate = lista.stream().map(Amortizacion::getFecha).max(Date::compareTo).get();

			if (string.equals("VIVOS") && maxDate.compareTo(date) > 0) {
				devueltos.add(existentes.get(i));
			}
			if (string.contentEquals("AMORTIZADOS") && maxDate.compareTo(date) <= 0) {
				devueltos.add(existentes.get(i));
			}
		}
		return devueltos;
	}

	@Override
	public Prestamo createPrestamo(Prestamo prestamo, Cuenta cuenta) throws PrestamoErroneoException {
		Calendar cal = Calendar.getInstance();
		Date actual = new Date(cal.getTimeInMillis());
		int controlador1 = 0;
		int controlador2 = 0;
		List<Prestamo> prestamosRealizados = new ArrayList<Prestamo>();
		prestamosRealizados = cuenta.getPrestamos();

		if (prestamosRealizados.isEmpty()) {
			controlador1 = 1;
		} else {
			List<Date> fechasmaxprestamos = new ArrayList<Date>();
			for (int i = 0; i < prestamosRealizados.size(); i++) {
				List<Amortizacion> lista = prestamosRealizados.get(i).getAmortizaciones();
				fechasmaxprestamos.add(lista.stream().map(Amortizacion::getFecha).max(Date::compareTo).get());
			}
			Date maxDateTotal = fechasmaxprestamos.stream().max(Date::compareTo).get();
			if (maxDateTotal.compareTo(actual) <= 0) {
				controlador2 = 1;
			}

		}

		if (controlador1 == 1 || controlador2 == 1) {
			prestamo.setCuenta(cuenta);
			prestamoRepository.save(prestamo);
			return prestamo;
		} else {
			throw new PrestamoErroneoException();
		}
	}

	@Override
	public PrestamoDtoReport findPrestamosbyIdReport(Integer id) throws PrestamoNotFoundException {
		try {
			Prestamo prestamo = prestamoRepository.findById(id).get();
			return modelmapper.map(prestamo, PrestamoDtoReport.class);
		} catch (NoSuchElementException e) {
			throw new PrestamoNotFoundException();
		}
	}

	@Override
	public List<PrestamoDtoReport> findPrestamoReports(String string) {
		List<Prestamo> todoslosprestamos = prestamoRepository.findAll();
		List<Prestamo> devueltos = new ArrayList<Prestamo>();
		Calendar cal = Calendar.getInstance();
		Date actual = new Date(cal.getTimeInMillis());
		for (int i = 0; i < todoslosprestamos.size(); i++) {
			List<Amortizacion> lista = todoslosprestamos.get(i).getAmortizaciones();
			Date maxDate = lista.stream().map(Amortizacion::getFecha).max(Date::compareTo).get();

			if (string.equals("VIVOS") && maxDate.compareTo(actual) > 0) {
				devueltos.add(todoslosprestamos.get(i));
			}
			if (string.contentEquals("AMORTIZADOS") && maxDate.compareTo(actual) <= 0) {
				devueltos.add(todoslosprestamos.get(i));
			}
		}
		return ObjectMapperUtils.mapAll(devueltos, PrestamoDtoReport.class);
	}
}
