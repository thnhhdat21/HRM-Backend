package vn.tdsoftware.hrm_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.tdsoftware.hrm_backend.entity.Department;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Department findByIdAndIsEnabled(Long id, Boolean isEnabled);
    Optional<List<Department> > findAllByDepartmentLevelAndIsEnabled(int level, Boolean isEnabled);
    boolean existsByIdAndIsEnabled(long id, Boolean isEnabled);
}
