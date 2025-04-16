package vn.tdsoftware.hrm_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.tdsoftware.hrm_backend.entity.JobPosition;

import java.util.List;

@Repository
public interface JobPositionRepository extends JpaRepository<JobPosition, Long> {
    JobPosition findByIdAndIsEnabled(long id, boolean isEnabled);
    List<JobPosition> findByDutyIdAndIsEnabled(long dutyId, boolean isEnabled);
}
