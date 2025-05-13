package vn.tdsoftware.hrm_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.tdsoftware.hrm_backend.entity.Contract;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {
    Optional<Contract> findByIdAndIsEnabled(Long id, boolean isEnabled);
    Optional<List<Contract>> findByParentAndIsEnabled(long parent, boolean isEnabled);
    Optional<Contract> findByEmployeeIdAndParentAndDateStartBetween(Long employeeId, Long parent, LocalDate dateStart, LocalDate dateEnd);
    Optional<List<Contract>> findAllByDateEndAndParentIsNullAndIsEnabled(LocalDate dateEnd, boolean isEnabled);
    Optional<List<Contract>> findAllByDateStartAndIsEnabled(LocalDate date, boolean isEnabled);
}
