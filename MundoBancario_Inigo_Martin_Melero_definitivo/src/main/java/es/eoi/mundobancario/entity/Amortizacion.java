package es.eoi.mundobancario.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "amortizaciones")
public class Amortizacion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "fecha")
	private Date fecha;

	@Column(name = "importe")
	private Double importe;

	@ManyToOne
	@JoinColumn(name = "id_prestamo", referencedColumnName = "id")
	private Prestamo prestamo;

	public Amortizacion(Date fecha, Double importe, Prestamo prestamo) {
		this.fecha = fecha;
		this.importe = importe;
		this.prestamo = prestamo;
	}

}
