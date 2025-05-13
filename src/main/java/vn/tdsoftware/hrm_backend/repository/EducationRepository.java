package vn.tdsoftware.hrm_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.tdsoftware.hrm_backend.entity.Education;

import java.util.List;
import java.util.Optional;

@Repository
public interface EducationRepository extends JpaRepository<Education, Long> {
    Optional<List<Education>> findAllByEmployeeIdAndIsEnabled(long employeeId, boolean isEnabled);
    Optional<Education> findByIdAndIsEnabled(long id, boolean isEnabled);
}
