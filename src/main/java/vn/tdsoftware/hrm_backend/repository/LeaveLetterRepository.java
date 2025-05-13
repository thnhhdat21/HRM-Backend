package vn.tdsoftware.hrm_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.tdsoftware.hrm_backend.entity.LeaveLetter;

import java.util.Optional;

@Repository
public interface LeaveLetterRepository extends JpaRepository<LeaveLetter, Long> {
    Optional<LeaveLetter> findByLetterIdAndIsEnabled(long letterId, boolean isEnabled);

}
