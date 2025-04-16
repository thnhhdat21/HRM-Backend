package vn.tdsoftware.hrm_backend.dto.workshift.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkShiftDetail {
    private Long id;
    private String code;
    private String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Ho_Chi_Minh")
    private LocalTime timeIn;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Ho_Chi_Minh")
    private LocalTime timeOut;

    private boolean nextDayEnabled;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Ho_Chi_Minh")
    private LocalTime breakStartTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Ho_Chi_Minh")
    private LocalTime breakEndTime;

    private Double totalTime;
    private Double totalWorkDay;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Ho_Chi_Minh")
    private LocalTime checkinFirst;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Ho_Chi_Minh")
    private LocalTime checkoutLater;

    private boolean autoTimeKeeping;
    private boolean autoCheckoutForPosition;
    private List<Long> jobPositions;
}
