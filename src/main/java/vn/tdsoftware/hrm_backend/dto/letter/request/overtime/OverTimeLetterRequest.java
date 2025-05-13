package vn.tdsoftware.hrm_backend.dto.letter.request.overtime;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class OverTimeLetterRequest {
    private LocalDate dateRegis;
    private long employeeId;
    private long letterId;
    private long letterReasonId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime timeStart;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime timeEnd;
    private double total;
    private Boolean isNextDay;
    private String description;
}
