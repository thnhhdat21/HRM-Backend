package vn.tdsoftware.hrm_backend.dto.letter.request.worktime;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class WorkTimeLetterRequest {
    private long letterId;
    private long employeeId;
    private long letterReasonId;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private String description;
}
