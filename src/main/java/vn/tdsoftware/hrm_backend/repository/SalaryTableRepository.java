package vn.tdsoftware.hrm_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.tdsoftware.hrm_backend.entity.SalaryTable;

import java.util.List;
import java.util.Optional;

@Repository
public interface SalaryTableRepository extends JpaRepository<SalaryTable, Long> {
    Optional<List<SalaryTable>> findByIsEnabledOrderByYearMonthDesc(boolean isEnabled);
}
