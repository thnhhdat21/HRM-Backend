package vn.tdsoftware.hrm_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.tdsoftware.hrm_backend.entity.WorkTimeLetter;

import java.util.Optional;

@Repository
public interface WorkTimeLetterRepository extends JpaRepository<WorkTimeLetter, Long> {
    Optional<WorkTimeLetter> findByLetterIdAndIsEnabled(long letterId, boolean isEnabled);
}
