package vn.tdsoftware.hrm_backend.dto.letter.response.overtime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OverTimeLetterResponse {
    private long letterId;
    private Boolean isNextDay;
    private long employeeId;
    private Long letterReasonId;
    private String letterReason;
    private int letterType;
    private int letterState;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:ss:mm")
    private LocalTime timeStart;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:ss:mm")
    private LocalTime timeEnd;
    private Double total;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateRegis;
    private String description;
}
