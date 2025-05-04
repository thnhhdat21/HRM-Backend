package vn.tdsoftware.hrm_backend.dto.lette.response.leave;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class LeaveLetterResponse {
    private String code;
    private LocalDate date;
    private long employeeId;
    private long letterReasonId;
    private int letterState;
    private LocalDateTime dateStart;
    private LocalDateTime dateEnd;
    private double total;
    private String description;
}
