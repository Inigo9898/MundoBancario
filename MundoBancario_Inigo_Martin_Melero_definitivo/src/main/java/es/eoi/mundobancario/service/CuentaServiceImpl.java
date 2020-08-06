package es.eoi.mundobancario.service;

import java.util.List;
import java.util.NoSuchElementException;

import javax.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import es.eoi.mundobancario.dto.CuentaDto;
import es.eoi.mundobancario.entity.Cuenta;
import es.eoi.mundobancario.exception.CuentaNotFoundException;
import es.eoi.mundobancario.exception.PagoErroneoException;
import es.eoi.mundobancario.repository.CuentaRepository;
import es.eoi.mundobancario.utils.ObjectMapperUtils;

@Service
public class CuentaServiceImpl implements CuentaService {

	@Autowired
	CuentaRepository cuentaRepository;
	@Autowired
	ModelMapper modelmapper;

	@Override
	public List<CuentaDto> findCuentasFromCliente(Integer id) throws CuentaNotFoundException {
		List<Cuenta> cuentas = cuentaRepository.findClienteById(id);
		if (cuentas.isEmpty()) {
			throw new CuentaNotFoundException();
		}
		return ObjectMapperUtils.mapAll(cuentas, CuentaDto.class);
	}

	@Override
	public List<CuentaDto> findAllCuentas() throws CuentaNotFoundException {
		List<Cuenta> listaTodasCuentas = cuentaRepository.findAll();
		if (listaTodasCuentas.isEmpty()) {
			throw new CuentaNotFoundException();
		}
		return ObjectMapperUtils.mapAll(listaTodasCuentas, CuentaDto.class);
	}

	@Override
	public List<CuentaDto> findCuentasDeudoras() throws CuentaNotFoundException {
		List<Cuenta> cuentasDeudoras = cuentaRepository.findCuentasDeudoras();
		if (cuentasDeudoras.isEmpty()) {
			throw new CuentaNotFoundException();
		}
		return ObjectMapperUtils.mapAll(cuentasDeudoras, CuentaDto.class);
	}

	@Override
	public CuentaDto findCuentabyId(Integer id) throws CuentaNotFoundException {
		try {
			Cuenta cuen = cuentaRepository.findById(id).get();
			return modelmapper.map(cuen, CuentaDto.class);
		} catch (NoSuchElementException e) {
			throw new CuentaNotFoundException();
		}
	}

	@Override
	public String saveCuenta(Cuenta cuenta) {

		if (cuenta.getAlias().length() > 20) {

			return "El alias tiene mas de 20 caracteres, por favor reduzcalo";

		} else if (cuenta.getSaldo() != (double) cuenta.getSaldo()) {

			return "El saldo debe ser un double";

		} else {
			cuentaRepository.save(cuenta);
			return "Se ha guardado con exito";
		}

	}

	@Override
	public String updateCuenta(Integer id, Cuenta cuenta) throws CuentaNotFoundException {

		try {

			Cuenta actualizada = cuentaRepository.getOne(id);
			if (cuenta.getAlias().length() < 20) {
				actualizada.setAlias(cuenta.getAlias());
				cuentaRepository.save(actualizada);
				return "Se ha actualizado correctamente la Cuenta";
			} else {
				return "Tienes que introducir menos de 20 caracteres de Alias";
			}
		} catch (EntityNotFoundException e) {
			throw new CuentaNotFoundException();
		}

	}

	@Override
	public Cuenta findCuentabyId2(Integer id) throws CuentaNotFoundException {
		try {
			return cuentaRepository.findById(id).get();
		} catch (NoSuchElementException e) {
			throw new CuentaNotFoundException();
		}
	}

	@Override
	public void cambiarsaldo(double importe, Integer id, String operacion) throws PagoErroneoException {
		Cuenta actualizada = cuentaRepository.findById(id).get();
		Double dinero_actual = actualizada.getSaldo();
		if (operacion.equals("INGRESO") || operacion.equals("PRESTAMO")) {
			actualizada.setSaldo(dinero_actual + importe);
		}

		else if (operacion.equals("PAGO") && dinero_actual - importe <= 0) {
			throw new PagoErroneoException();
		} else {
			actualizada.setSaldo(dinero_actual - importe);
		}
		cuentaRepository.save(actualizada);
	}
}
