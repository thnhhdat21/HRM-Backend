package vn.tdsoftware.hrm_backend.dto.salarytable.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.YearMonth;

@Getter
@Setter
@Builder
public class SalaryMonth {
    private Long salaryDetailId;
    private YearMonth yearMonth;
    private int salary;
    private int tax;
}
