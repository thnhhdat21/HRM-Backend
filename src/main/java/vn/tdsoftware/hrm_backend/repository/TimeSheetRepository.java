package vn.tdsoftware.hrm_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.tdsoftware.hrm_backend.entity.TimeSheet;

import java.util.Optional;

@Repository
public interface TimeSheetRepository extends JpaRepository<TimeSheet, Integer> {
    Optional<TimeSheet> findByYearMonthAndIsEnabled(String yearMonth, boolean isEnabled);
}
