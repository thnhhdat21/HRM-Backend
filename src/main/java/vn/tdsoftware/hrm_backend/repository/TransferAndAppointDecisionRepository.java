package vn.tdsoftware.hrm_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.tdsoftware.hrm_backend.entity.TransferAndAppointDecision;

import java.util.Optional;

@Repository
public interface TransferAndAppointDecisionRepository extends JpaRepository<TransferAndAppointDecision, Long> {
    Optional<TransferAndAppointDecision> findByIdAndIsEnabled(long id, boolean isEnabled);
    Optional<TransferAndAppointDecision> findByDecisionIdAndIsEnabled(long decisionId, boolean isEnabled);
    TransferAndAppointDecision findByDecisionId(long decisionId);

}
