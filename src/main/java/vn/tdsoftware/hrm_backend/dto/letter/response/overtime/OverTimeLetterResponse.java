package vn.tdsoftware.hrm_backend.dto.lette.response.overtime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDate;

@Getter
@Setter
@Builder
public class OverTimeLetterResponse {
    private String code;
    private LocalDate date;
    private long employeeId;
    private long letterReasonId;
    private int letterState;
    private Timestamp timeStart;
    private Timestamp timeEnd;
    private double total;
    private String description;
}
