package es.eoi.mundobancario.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import es.eoi.mundobancario.entity.Movimiento;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Integer> {
	
	@Query(value = "SELECT * FROM MOVIMIENTOS WHERE ID_CUENTA = ?1", nativeQuery = true)
	List<Movimiento> findMovimientoCuenta(Integer id);
}
