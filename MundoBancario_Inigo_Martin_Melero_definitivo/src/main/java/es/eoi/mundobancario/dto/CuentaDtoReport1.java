package es.eoi.mundobancario.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CuentaDtoReport1 {

	private String alias;

	private Double saldo;

	private List<MovimientoDtoReport> movimientos;

}
