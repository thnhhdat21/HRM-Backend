package vn.tdsoftware.hrm_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.tdsoftware.hrm_backend.entity.SalaryDecisionHasAllowance;

import java.util.List;
import java.util.Optional;

@Repository
public interface SalaryDecisionHasAllowanceRepository extends JpaRepository<SalaryDecisionHasAllowance, Long> {
    Optional<SalaryDecisionHasAllowance> findByIdAndIsEnabled(long id, boolean isEnabled);
    Optional<List<SalaryDecisionHasAllowance>> findBySalaryDecisionIdAndIsEnabled(long id, boolean isEnabled);
}

