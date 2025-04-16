package vn.tdsoftware.hrm_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.tdsoftware.hrm_backend.entity.RewardAndPenalty;

import java.util.Optional;

@Repository
public interface RewardAndPenaltyRepository extends JpaRepository<RewardAndPenalty, Long> {
    Optional<RewardAndPenalty> findByIdAndIsEnabled(Long id, Boolean isEnabled);
}
