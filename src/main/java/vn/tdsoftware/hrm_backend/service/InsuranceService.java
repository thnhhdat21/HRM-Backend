package vn.tdsoftware.hrm_backend.service;

import vn.tdsoftware.hrm_backend.dto.employee.request.EmployeeFilter;
import vn.tdsoftware.hrm_backend.dto.insurance.response.InsuranceResponse;

import java.util.List;

public interface InsuranceService {
    List<InsuranceResponse> getListInsurance(EmployeeFilter filter);
    int getCountInsurance(EmployeeFilter filter);
}
