package vn.tdsoftware.hrm_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.tdsoftware.hrm_backend.entity.OverTimeLetter;

import java.util.Optional;

@Repository
public interface OverTimeLetterRepository extends JpaRepository<OverTimeLetter, Long> {
    Optional<OverTimeLetter> findByLetterIdAndIsEnabled(long letterId, boolean isEnabled);
}
