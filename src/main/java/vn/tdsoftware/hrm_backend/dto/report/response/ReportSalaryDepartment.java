package vn.tdsoftware.hrm_backend.dto.report.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ReportSalaryDepartment {
    private String department;
    private int sumSalary;
}
