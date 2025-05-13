package vn.tdsoftware.hrm_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.tdsoftware.hrm_backend.entity.InOutAndEndWorkLetter;

import java.util.Optional;

@Repository
public interface InOutAndEndWorkLetterRepository extends JpaRepository<InOutAndEndWorkLetter, Long> {
    Optional<InOutAndEndWorkLetter> findByLetterIdAndIsEnabled(long letterId, boolean isEnabled);
}
