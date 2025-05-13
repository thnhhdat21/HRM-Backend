package vn.tdsoftware.hrm_backend.dto.timekeeping.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@ToString
public class EmployeeTimeKeeping {
    private long employeeId;
    private String employeeCode;
    private String employeeName;
    private String department;
    private String jobPosition;

    private Double onLeaveTotal;
    private Double onLeaveUsed;
    private Double overTimeTotal;
    private boolean isLate;

    private LocalDate dateWorking;
    private double workDay;
    private String symbolLetter;
}
