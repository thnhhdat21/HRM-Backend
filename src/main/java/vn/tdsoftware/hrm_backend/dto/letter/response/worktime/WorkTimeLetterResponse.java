package vn.tdsoftware.hrm_backend.dto.lette.response.worktime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@Builder
public class WorkTimeLetterResponse {
    private String code;
    private LocalDate date;
    private long employeeId;
    private long letterReasonId;
    private int letterState;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private String description;
}
