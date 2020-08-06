package es.eoi.mundobancario.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import es.eoi.mundobancario.enums.TipoMovimientoBancario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "tipos_movimiento")
public class TipoMovimiento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "tipo")
	@Enumerated(EnumType.STRING)
	private TipoMovimientoBancario tipo;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "tipomovimiento")
	private List<Movimiento> movimientos;

	public TipoMovimiento(TipoMovimientoBancario tipo) {

		this.tipo = tipo;
	}
}
