package vn.tdsoftware.hrm_backend.service;

import vn.tdsoftware.hrm_backend.dto.employee.request.EmployeeFilter;
import vn.tdsoftware.hrm_backend.dto.salarytable.response.SalaryDetailResponse;
import vn.tdsoftware.hrm_backend.dto.salarytable.response.SalaryTableResponse;
import vn.tdsoftware.hrm_backend.dto.salarytable.response.TaxResponse;

import java.util.List;

public interface SalaryTableService {
    List<SalaryTableResponse> getListSalaryTable();
    List<SalaryDetailResponse> getListSalaryDetail(EmployeeFilter filter);
    int getCountSalaryDetail(EmployeeFilter filter);
    List<TaxResponse> getListTax(EmployeeFilter filter);
    int getCountTax(EmployeeFilter filter);
}
