package vn.tdsoftware.hrm_backend.dto.employee.response;

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
public class EmployeeTimeSheet {
    private LocalDate dateWorking;
    private double workDay;
    private LocalTime timeIn;
    private LocalTime timeOut;
    private LocalTime timeLate;
    private LocalTime timeEarly;
    private boolean isLate;
    private Long letterId;
    private Integer letterState;
}
