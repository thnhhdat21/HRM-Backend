package vn.tdsoftware.hrm_backend.dto.timekeeping.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class TimeKeepingResponse {
    private LocalDate dateWorking;
    private double workDay;
    private String symbolLetter;
    private boolean isLate;
}
