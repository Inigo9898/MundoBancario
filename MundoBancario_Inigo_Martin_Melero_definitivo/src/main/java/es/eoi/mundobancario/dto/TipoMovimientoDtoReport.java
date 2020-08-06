package es.eoi.mundobancario.dto;

import es.eoi.mundobancario.enums.TipoMovimientoBancario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class TipoMovimientoDtoReport {
	
	private TipoMovimientoBancario tipo;
}
