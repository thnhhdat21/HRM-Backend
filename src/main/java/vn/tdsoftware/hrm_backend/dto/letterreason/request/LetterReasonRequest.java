package vn.tdsoftware.hrm_backend.dto.letterreason.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class LetterReasonRequest {
    private Long id;
    private String reason;
    private String symbol;
    private Integer maximum;
    private String unit;
    private Boolean workDayEnabled;
    private LocalTime goLate;
    private LocalTime backEarly;
    private Integer type;
    private String des;
}
