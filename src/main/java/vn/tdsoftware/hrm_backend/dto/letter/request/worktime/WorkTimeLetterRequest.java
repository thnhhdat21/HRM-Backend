package vn.tdsoftware.hrm_backend.dto.lette.request.worktime;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class WorkTimeLetterRequest {
    private String code;
    private LocalDate date;
    private long employeeId;
    private long letterId;
    private long letterReasonId;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private String description;
}
