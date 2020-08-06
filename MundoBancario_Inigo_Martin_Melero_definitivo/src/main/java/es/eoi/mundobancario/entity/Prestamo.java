package es.eoi.mundobancario.entity;

import java.sql.Date;
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

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "prestamos")
public class Prestamo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "descripcion")
	private String descripcion;

	@Column(name = "fecha")
	private Date fecha;

	@Column(name = "importe")
	private Double importe;

	@Column(name = "plazos")
	private Integer plazos;

	@ManyToOne
	@JoinColumn(name = "id_cuenta", referencedColumnName = "num_cuenta")
	private Cuenta cuenta;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "prestamo")
	private List<Amortizacion> amortizaciones;

}
