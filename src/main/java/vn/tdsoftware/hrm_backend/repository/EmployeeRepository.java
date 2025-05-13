package vn.tdsoftware.hrm_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.tdsoftware.hrm_backend.entity.Employee;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByIdAndIsEnabled(Long id, Boolean isEnabled);
    Employee findByEmployeeCodeAndIsEnabled(String employeeCode, Boolean isEnabled);
    List<Employee> findAllByStatusAndIsEnabled(int status, boolean isEnabled);
    List<Employee> findAllByStatusNotAndIsEnabled(int status, boolean isEnabled);
    Optional<Employee> findByDateJoinAndIsEnabled(LocalDate dateJoin, Boolean isEnabled);

}
