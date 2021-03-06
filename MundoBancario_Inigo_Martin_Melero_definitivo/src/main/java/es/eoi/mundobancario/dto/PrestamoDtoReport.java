package es.eoi.mundobancario.dto;

import java.sql.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class PrestamoDtoReport {

	private String descripcion;

	private Date fecha;

	private Double importe;

	private Integer plazos;

	private CuentaDtoReport2 cuenta;

	private List<AmortizacionDtoReport> amortizaciones;
}
