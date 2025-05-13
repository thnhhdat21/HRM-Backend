package vn.tdsoftware.hrm_backend.dao;

import org.springframework.stereotype.Repository;
import vn.tdsoftware.hrm_backend.dto.employee.request.EmployeeFilter;
import vn.tdsoftware.hrm_backend.dto.insurance.response.InsuranceResponse;

import java.util.List;

@Repository
public interface InsuranceDAO {
    List<InsuranceResponse> getListInsurance(EmployeeFilter filter);
    int getCountInsurance(EmployeeFilter filter);
}
