package vn.tdsoftware.hrm_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.tdsoftware.hrm_backend.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findByIdAndIsEnabled(Long id, Boolean isEnabled);
    Employee findByEmployeeCodeAndIsEnabled(String employeeCode, Boolean isEnabled);
}
