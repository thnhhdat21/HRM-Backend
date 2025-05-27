package vn.tdsoftware.hrm_backend.dao;

import org.springframework.stereotype.Repository;
import vn.tdsoftware.hrm_backend.dto.report.response.ReportCountEmployee;
import vn.tdsoftware.hrm_backend.dto.report.response.ReportSalaryDepartment;

import java.time.YearMonth;
import java.util.List;

@Repository
public interface ReportDAO {
    List<ReportCountEmployee> reportCountEmployee();
    List<ReportSalaryDepartment> reportSalaryDepartment(YearMonth yearMonth);
}
