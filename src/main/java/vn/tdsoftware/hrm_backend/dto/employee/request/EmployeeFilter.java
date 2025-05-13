package vn.tdsoftware.hrm_backend.dto.employee.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.YearMonth;
import java.util.List;

@Getter
@Setter
@ToString
public class EmployeeFilter {
    private String name;
    private Integer type;
    private Integer status;
    private List<Long> department;
    private List<Long> jobPosition;
    private List<Long> duty;
    private String dateJoin;
    private YearMonth yearMonth;
    private int year;
    private int salaryTableId;
    private int pageIndex;
}
