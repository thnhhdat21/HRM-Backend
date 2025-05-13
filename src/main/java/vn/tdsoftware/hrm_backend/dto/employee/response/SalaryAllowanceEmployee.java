package vn.tdsoftware.hrm_backend.dto.employee.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import vn.tdsoftware.hrm_backend.dto.allowance.response.AllowanceResponse;

import java.util.List;

@Getter
@Setter
@Builder
public class SalaryAllowanceEmployee {
    private long employeeId;
    private int salaryGross;
    private List<AllowanceResponse> allowances;
}
