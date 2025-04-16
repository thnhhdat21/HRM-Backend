package vn.tdsoftware.hrm_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.tdsoftware.hrm_backend.entity.WorkShift;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkShiftRepository extends JpaRepository<WorkShift, Long> {
    Optional<List<WorkShift>> findAllByIsEnabled(boolean isEnabled);
    Optional<WorkShift> findByIdAndIsEnabled(Long id, boolean isEnabled);
}
