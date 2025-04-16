package vn.tdsoftware.hrm_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.tdsoftware.hrm_backend.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Department findByIdAndIsEnabled(Long id, Boolean isEnabled);
}
