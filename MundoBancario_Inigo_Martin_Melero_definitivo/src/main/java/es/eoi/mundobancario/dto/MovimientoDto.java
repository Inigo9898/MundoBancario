package es.eoi.mundobancario.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor

public class MovimientoDto {

	private String descripcion;

	private Date fecha;

	private Double importe;

	private TipoMovimientoDto tipomovimiento;
}
