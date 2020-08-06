package es.eoi.mundobancario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import es.eoi.mundobancario.entity.TipoMovimiento;

@Repository
public interface TipoMovimientoRepository extends JpaRepository<TipoMovimiento, Integer> {

	@Query(value = "SELECT * FROM TIPOS_MOVIMIENTO WHERE TIPO = ?1", nativeQuery = true)
	TipoMovimiento findTipoMovimiento(String string);
	
}
