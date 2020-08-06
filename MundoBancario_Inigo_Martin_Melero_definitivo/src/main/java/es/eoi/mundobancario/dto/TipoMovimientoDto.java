package es.eoi.mundobancario.dto;

import es.eoi.mundobancario.enums.TipoMovimientoBancario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class TipoMovimientoDto {

	private TipoMovimientoBancario tipo;

}
