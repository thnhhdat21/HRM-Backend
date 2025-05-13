package vn.tdsoftware.hrm_backend.dto.letter.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LeaveLetterUnpaidSalary {
    private long employeeId;
}
