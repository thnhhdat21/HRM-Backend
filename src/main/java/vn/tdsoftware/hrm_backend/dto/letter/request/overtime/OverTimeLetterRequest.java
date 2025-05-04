package vn.tdsoftware.hrm_backend.dto.lette.request.overtime;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDate;

@Getter
@Setter
public class OverTimeLetterRequest {
    private String code;
    private LocalDate date;
    private long employeeId;
    private long letterId;
    private long letterReasonId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private Timestamp timeStart;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private Timestamp timeEnd;
    private double total;
    private String description;
}
