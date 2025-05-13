package vn.tdsoftware.hrm_backend.dao;

import org.springframework.stereotype.Repository;
import vn.tdsoftware.hrm_backend.dto.employee.request.EmployeeFilter;
import vn.tdsoftware.hrm_backend.dto.salarytable.response.SalaryDetailResponse;
import vn.tdsoftware.hrm_backend.dto.salarytable.response.TaxDTO;

import java.util.List;

@Repository
public interface SalaryTableDetailDAO {
    List<SalaryDetailResponse> getListSalaryDetail(EmployeeFilter filter);
    int getCountSalaryDetail(EmployeeFilter filter);
    List<TaxDTO> getListTax(EmployeeFilter filter);
    int getCountTax(EmployeeFilter filter);
}
