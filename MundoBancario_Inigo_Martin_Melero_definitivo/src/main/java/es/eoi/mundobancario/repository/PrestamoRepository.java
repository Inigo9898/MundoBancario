package es.eoi.mundobancario.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import es.eoi.mundobancario.entity.Prestamo;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Integer> {
	
	@Query(value = "SELECT * FROM PRESTAMOS WHERE ID_CUENTA = ?1", nativeQuery = true)
	List<Prestamo> findPrestamosCuenta(Integer id);

	@Query(value = "SELECT * FROM PRESTAMOS WHERE ID_CUENTA = ?1", nativeQuery = true)
	List<Prestamo> findPrestamosVivosCuenta(Integer id);
}
