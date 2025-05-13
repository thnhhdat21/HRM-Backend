package vn.tdsoftware.hrm_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.tdsoftware.hrm_backend.entity.OnLeave;

import java.util.Optional;

@Repository
public interface OnLeaveRepository extends JpaRepository<OnLeave, Long> {
    Optional<OnLeave> findByEmployeeIdAndYearAndIsEnabled(Long employeeId, Integer year, Boolean isEnabled);
}
