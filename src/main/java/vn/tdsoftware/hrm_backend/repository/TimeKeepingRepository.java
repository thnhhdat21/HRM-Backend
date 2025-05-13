package vn.tdsoftware.hrm_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.tdsoftware.hrm_backend.entity.TimeKeeping;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TimeKeepingRepository extends JpaRepository<TimeKeeping, Long> {
    Optional<TimeKeeping> findByEmployeeIdAndDate(Long employeeId, LocalDate date);
    boolean existsByEmployeeIdAndDate(Long employeeId, LocalDate date);
    Optional<List<TimeKeeping>> findByEmployeeIdAndDateBetween(Long employeeId, LocalDate start, LocalDate end);

}
