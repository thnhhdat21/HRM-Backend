package vn.tdsoftware.hrm_backend.service;

import vn.tdsoftware.hrm_backend.dto.report.response.ReportCountEmployee;
import vn.tdsoftware.hrm_backend.dto.report.response.ReportSalaryDepartment;
import vn.tdsoftware.hrm_backend.dto.report.response.ReportSalaryMonth;

import java.time.YearMonth;
import java.util.List;

public interface ReportService {
    List<ReportCountEmployee> reportCountEmployee();
    List<ReportSalaryDepartment> reportSalaryDepartment(YearMonth yearMonth);
}
