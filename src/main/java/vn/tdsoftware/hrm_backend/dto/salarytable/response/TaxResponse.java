package vn.tdsoftware.hrm_backend.dto.salarytable.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaxResponse {
    private long employeeId;
    private String employeeCode;
    private String employeeName;
    private String department;
    private String jobPosition;
    private List<SalaryMonth> salaryMonths;
}
