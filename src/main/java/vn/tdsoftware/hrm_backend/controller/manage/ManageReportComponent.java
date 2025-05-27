package vn.tdsoftware.hrm_backend.controller.manage;


import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.tdsoftware.hrm_backend.common.dto.response.ResponseData;
import vn.tdsoftware.hrm_backend.dto.report.response.ReportCountEmployee;
import vn.tdsoftware.hrm_backend.dto.report.response.ReportSalaryDepartment;
import vn.tdsoftware.hrm_backend.dto.report.response.ReportSalaryMonth;
import vn.tdsoftware.hrm_backend.service.ReportService;

import java.time.YearMonth;
import java.util.List;

@RestController
@RequestMapping("/manage-report")
@RequiredArgsConstructor
public class ManageReportComponent {
    private final ReportService reportService;

    @PostMapping("/report-count-employee")
    @PreAuthorize("hasAnyAuthority('ROLE_WATCH_REPORT', 'ADMIN')")
    public ResponseData<List<ReportCountEmployee>> reportCountEmployee() {
        List<ReportCountEmployee> response = reportService.reportCountEmployee();
        return ResponseData.<List<ReportCountEmployee>>builder()
                .code(1000)
                .data(response)
                .message("successfully")
                .build();
    }

    @PostMapping("/report-salary-department")
    @PreAuthorize("hasAnyAuthority('ROLE_WATCH_REPORT', 'ADMIN')")
    public ResponseData<List<ReportSalaryDepartment>> reportSalaryDepartment(@RequestParam("yearMonth") YearMonth yearMonth) {
        List<ReportSalaryDepartment> response = reportService.reportSalaryDepartment(yearMonth);
        return ResponseData.<List<ReportSalaryDepartment>>builder()
                .code(1000)
                .data(response)
                .message("successfully")
                .build();
    }
}
