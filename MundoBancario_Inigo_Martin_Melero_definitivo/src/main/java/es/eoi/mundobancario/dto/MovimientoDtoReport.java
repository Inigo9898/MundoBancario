package es.eoi.mundobancario.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class MovimientoDtoReport {
	private String descripcion;

	private Date fecha;

	private Double importe;

	private TipoMovimientoDtoReport tipomovimiento;
}
