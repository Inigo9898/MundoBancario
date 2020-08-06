package es.eoi.mundobancario.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eoi.mundobancario.entity.TipoMovimiento;
import es.eoi.mundobancario.repository.TipoMovimientoRepository;

@Service
public class TipoMovimientoServiceImpl implements TipoMovimientoService {

	@Autowired
	TipoMovimientoRepository tipoMovimientoRepository;

	@Override
	public TipoMovimiento ReadTipoMovimiento(String tipo) {

		return tipoMovimientoRepository.findTipoMovimiento(tipo);

	}

}
