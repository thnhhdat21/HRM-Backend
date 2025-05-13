package vn.tdsoftware.hrm_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.tdsoftware.hrm_backend.entity.Holiday;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface HolidayRepository extends JpaRepository<Holiday, Long> {
    Optional<Holiday> findByIdAndIsEnabled(long id, boolean isEnabled);
    Optional<List<Holiday>> findAllByIsEnabledAndDateBetween(boolean isEnabled, LocalDate startDate, LocalDate endDate);
    boolean existsByDateAndIsEnabled(LocalDate date, boolean isEnabled);
}
