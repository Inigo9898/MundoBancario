package es.eoi.mundobancario.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "cuentas")
public class Cuenta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer num_cuenta;

	@Column(name = "alias")
	private String alias;

	@Column(name = "saldo")
	private Double saldo;

	@ManyToOne
	@JoinColumn(name = "id_cliente", referencedColumnName = "id")
	private Cliente cliente;

	@JsonProperty(access = Access.READ_ONLY)
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "cuenta")
	private List<Prestamo> prestamos;

	@JsonProperty(access = Access.READ_ONLY)
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "cuenta")
	private List<Movimiento> movimientos;

	public Cuenta(Integer num_cuenta) {
		this.num_cuenta = num_cuenta;

	}

}
