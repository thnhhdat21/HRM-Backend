package vn.tdsoftware.hrm_backend.dto.salarytable.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.YearMonth;


@Getter
@Setter
@Builder
public class TaxDTO {
    private long employeeId;
    private String employeeCode;
    private String employeeName;
    private String department;
    private String jobPosition;
    private YearMonth yearMonth;
    private int salary;
    private int tax;
}
