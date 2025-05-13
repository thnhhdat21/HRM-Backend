package vn.tdsoftware.hrm_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.tdsoftware.hrm_backend.entity.Family;

import java.util.List;
import java.util.Optional;

@Repository
public interface FamilyRepository extends JpaRepository<Family, Long> {
    Optional<List<Family>> findAllByEmployeeIdAndIsEnabled(long id, boolean isEnabled);
    Optional<Family> findByIdAndIsEnabled(long id, boolean isEnabled);
    int countByEmployeeIdAndDependentAndIsEnabled(long employeeId, boolean dependent, boolean isEnabled);
}
