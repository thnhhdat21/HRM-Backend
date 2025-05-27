package vn.tdsoftware.hrm_backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.tdsoftware.hrm_backend.common.exception.BusinessException;
import vn.tdsoftware.hrm_backend.dao.ReportDAO;
import vn.tdsoftware.hrm_backend.dto.report.response.ReportCountEmployee;
import vn.tdsoftware.hrm_backend.dto.report.response.ReportSalaryDepartment;
import vn.tdsoftware.hrm_backend.enums.ErrorCode;
import vn.tdsoftware.hrm_backend.service.ReportService;

import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final ReportDAO reportDAO;

    @Override
    public List<ReportCountEmployee> reportCountEmployee() {
        List<ReportCountEmployee> response = reportDAO.reportCountEmployee();
        if (response.isEmpty()) {
            throw new BusinessException(ErrorCode.REPORT_COUNT_EMPLOYEE_IS_EMPTY);
        }
        return response;
    }

    @Override
    public List<ReportSalaryDepartment> reportSalaryDepartment(YearMonth yearMonth) {
        List<ReportSalaryDepartment> response = reportDAO.reportSalaryDepartment(yearMonth);
        if (response.isEmpty()) {
            throw new BusinessException(ErrorCode.REPORT_SALARY_DEPARTMENT_IS_EMPTY);
        }
        return response;
    }
}
