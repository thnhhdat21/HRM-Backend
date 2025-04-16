package vn.tdsoftware.hrm_backend.dto.workshift.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@Builder
public class WorkShiftResponse {
    private Long id;
    private String code;
    private String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Ho_Chi_Minh")
    private LocalTime timeIn;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Ho_Chi_Minh")
    private LocalTime timeOut;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Ho_Chi_Minh")
    private LocalTime breakStartTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Ho_Chi_Minh")
    private LocalTime breakEndTime;

    private Double totalTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Ho_Chi_Minh")
    private LocalTime checkinFirst;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:ss", timezone = "Asia/Ho_Chi_Minh")
    private LocalTime checkoutLater;
}
