package vn.tdsoftware.hrm_backend.dto.salarytable.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SalaryDetailResponse {
    private long employeeId;
    private String employeeCode;
    private String employeeName;
    private String department;
    private String jobPosition;
    private int workDayReal;
    private int totalWorkDay;
    private int salaryGross;
    private int salaryWorkDay;
    private int totalAllowance;
    private int reward;
    private int penalty;
    private int salaryOTOnWeek;
    private int salaryOTLastWeek;
    private int salaryOTHoliday;
    private int totalInsurance;
    private int totalTax;
    private int salaryReal;
}
