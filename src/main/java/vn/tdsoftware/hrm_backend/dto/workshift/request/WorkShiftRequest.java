package vn.tdsoftware.hrm_backend.dto.workshift.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@ToString
public class WorkShiftRequest {
    private Long id;
    private String code;
    private String name;
    private LocalTime timeIn;
    private LocalTime timeOut;
    private boolean nextDayEnabled;
    private LocalTime breakStartTime;
    private LocalTime breakEndTime;
    private Double totalTime;
    private Double totalWorkDay;
    private LocalTime checkinFirst;
    private LocalTime checkoutLater;
    private boolean autoTimeKeeping;
    private boolean autoCheckoutForPosition;
    private List<Long> jobPositions;
}
