package vn.tdsoftware.hrm_backend.service;

import vn.tdsoftware.hrm_backend.dto.employee.request.EmployeeFilter;
import vn.tdsoftware.hrm_backend.dto.onleave.request.OnLeaveRequest;
import vn.tdsoftware.hrm_backend.dto.onleave.response.EmployeeOnLeaveResponse;
import vn.tdsoftware.hrm_backend.dto.onleave.response.OnLeaveResponse;

import java.util.List;

public interface OnLeaveService {
    OnLeaveResponse getOnLeaveProfile(long employeeId);
    void updateOnLeaveProfile(OnLeaveRequest request);
    List<EmployeeOnLeaveResponse> getEmployeeOnLeave(EmployeeFilter filter);
    int getCountEmployee(EmployeeFilter filter);
}
