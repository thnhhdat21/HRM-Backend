package vn.tdsoftware.hrm_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.tdsoftware.hrm_backend.entity.ContractGeneral;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ContractGeneralRepository extends JpaRepository<ContractGeneral, Long> {
    Optional<ContractGeneral> findByEmployeeId(long employeeId);
    boolean existsByEmployeeId(long employeeId);
    List<ContractGeneral> findAllByDateLiquidation(LocalDate dateLiquidation);
}
