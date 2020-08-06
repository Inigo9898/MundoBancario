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

public class ClienteDtoReport1 {

	private String usuario;

	private String pass;

	private String nombre;

	private String email;

	private List<CuentaDtoReport1> cuentas;
}
