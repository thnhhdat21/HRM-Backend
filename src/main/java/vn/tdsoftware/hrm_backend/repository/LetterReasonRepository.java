package vn.tdsoftware.hrm_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.tdsoftware.hrm_backend.entity.LetterReason;

import java.util.List;
import java.util.Optional;

@Repository
public interface LetterReasonRepository extends JpaRepository<LetterReason, Long> {
    Optional<List<LetterReason>> findByLetterTypeIdAndIsEnabled(int letterTypeId, boolean isEnabled);
    Optional<LetterReason> findByIdAndIsEnabled(long id, boolean isEnabled);
}
