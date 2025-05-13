package vn.tdsoftware.hrm_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.tdsoftware.hrm_backend.entity.RewardAndPenaltyDecision;

import java.util.Optional;

@Repository
public interface RewardAndPenaltyDecisionRepository extends JpaRepository<RewardAndPenaltyDecision, Long> {
    Optional<RewardAndPenaltyDecision> findByDecisionIdAndIsEnabled(long decisionId, boolean isEnabled);
    RewardAndPenaltyDecision findByDecisionId(Long decisionId);
}
