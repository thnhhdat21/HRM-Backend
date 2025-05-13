package vn.tdsoftware.hrm_backend.dto.letter.response.worktime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Builder
public class WorkTimeLetterResponse {
    private long letterId;
    private long employeeId;
    private long letterReasonId;
    private String letterReason;
    private int letterType;
    private int letterState;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private LocalTime goLate;
    private LocalTime backEarly;
    private String description;
}
