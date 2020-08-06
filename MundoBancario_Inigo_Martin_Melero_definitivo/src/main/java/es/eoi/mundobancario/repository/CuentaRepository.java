package es.eoi.mundobancario.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import es.eoi.mundobancario.entity.Cuenta;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, Integer> {

	@Query(value = "SELECT * FROM CUENTAS WHERE ID_CLIENTE = ?1", nativeQuery = true)
	List<Cuenta> findClienteById(Integer id);

	@Query(value = "SELECT * FROM CUENTAS WHERE SALDO < 0", nativeQuery = true)
	List<Cuenta> findCuentasDeudoras();

}
