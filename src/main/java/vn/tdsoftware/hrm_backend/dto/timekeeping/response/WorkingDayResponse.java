package vn.tdsoftware.hrm_backend.dto.timekeeping.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@Builder
public class WorkingDayResponse {
    private double workDay;
    private boolean isLate;
    private LocalTime checkin;
    private LocalTime checkout;
    private LocalTime timeLate;
    private LocalTime timeEarly;
    private List<Long> letterIds;
}
