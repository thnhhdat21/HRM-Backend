package vn.tdsoftware.hrm_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.tdsoftware.hrm_backend.entity.SalaryDecisionHasAllowance;

@Repository
public interface SalaryDecisionHasAllowanceRepository extends JpaRepository<SalaryDecisionHasAllowance, Long> {
}
