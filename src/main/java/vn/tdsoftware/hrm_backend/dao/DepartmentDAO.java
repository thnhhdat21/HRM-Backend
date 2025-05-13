package vn.tdsoftware.hrm_backend.dao;

import org.springframework.stereotype.Repository;
import vn.tdsoftware.hrm_backend.dto.department.response.DepartmentTreeResponse;

import java.util.List;


@Repository
public interface DepartmentDAO {
   List<DepartmentTreeResponse> findAll();
   int countEmployeeInDepartment(Long id);
   void deleteDepartment(Long id);
   Long getDepartmentByEmployeeId(long employeeId);
}
