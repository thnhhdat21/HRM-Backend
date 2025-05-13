package vn.tdsoftware.hrm_backend.dto.letter.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@Builder
public class WorkTimeEmployee {
    private long employeeId;
    private long letterId;
    private LocalTime goLate;
    private LocalTime backEarly;
}
