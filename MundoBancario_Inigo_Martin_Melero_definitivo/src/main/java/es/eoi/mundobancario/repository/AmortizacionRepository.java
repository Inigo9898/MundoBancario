package es.eoi.mundobancario.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import es.eoi.mundobancario.entity.Amortizacion;

@Repository
public interface AmortizacionRepository extends JpaRepository<Amortizacion, Integer> {

	@Query(value = "SELECT * FROM AMORTIZACIONES WHERE FECHA = ?1", nativeQuery = true)
	List<Amortizacion> findAmortizacionesActuales(Date date);
}
