package vn.tdsoftware.hrm_backend.dao;

import org.springframework.stereotype.Repository;
import vn.tdsoftware.hrm_backend.dto.salarytable.response.SalaryTableResponse;

import java.util.List;

@Repository
public interface SalaryTableDAO {
    List<SalaryTableResponse> getListSalaryTableOfDepartment(long departmentId);

}
