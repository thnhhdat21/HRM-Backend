package vn.tdsoftware.hrm_backend.dao;

import org.springframework.stereotype.Repository;
import vn.tdsoftware.hrm_backend.dto.employee.request.EmployeeFilter;
import vn.tdsoftware.hrm_backend.dto.onleave.response.EmployeeOnLeaveResponse;
import vn.tdsoftware.hrm_backend.dto.onleave.response.OnLeaveResponse;

import java.util.List;

@Repository
public interface OnLeaveDAO {
    List<EmployeeOnLeaveResponse> getEmployeeOnLeave(EmployeeFilter filter);
    int getCountEmployee(EmployeeFilter filter);
}
