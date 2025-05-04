package vn.tdsoftware.hrm_backend.dto.lette.request.inoutandendword;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class InOutAndEndWorkRequest {
    private String code;
    private long employeeId;
    private long letterId;
    private long letterReasonId;
    private LocalDate date;
    private String description;
}
