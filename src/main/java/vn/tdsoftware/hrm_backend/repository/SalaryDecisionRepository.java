package vn.tdsoftware.hrm_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.tdsoftware.hrm_backend.entity.SalaryDecision;

import java.util.Optional;

@Repository
public interface SalaryDecisionRepository extends JpaRepository<SalaryDecision, Long> {
    Optional<SalaryDecision> findByDecisionIdAndIsEnabled(long decisionId, boolean isEnabled);
    SalaryDecision findByDecisionId(long decisionId);
}
